package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Service;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.*;

@Service
public class I18nMessageSourceImpl extends AbstractMessageSource implements MessageSource {

    public static final String MISSING_PROPERTY_INDICATOR = "??##**!!__";
    private static final String BASENAME = "language";
    private static final int MINUTES = 5;
    private final PortletService portletService;
    private final Map<Locale, ResourceBundle> resourceBundles;
    private DateTime timestamp;
    private boolean toUpdate;

    @Autowired
    public I18nMessageSourceImpl(PortletService portletService) {
        this.portletService = portletService;
        resourceBundles = new HashMap<Locale, ResourceBundle>();
        timestamp = new DateTime();
        toUpdate = true;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {

        ResourceBundle resourceBundle = getResourceBundle(locale);

        String text;
        try {
            text = resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            text = MISSING_PROPERTY_INDICATOR + code + "_" + locale;
        }

        return new MessageFormat(text);
    }

    private ResourceBundle getResourceBundle(Locale locale) {

        if (!isResourceBundleUpToDate()) {
            regenerateBundleFiles();
        }

        ResourceBundle resourceBundle = resourceBundles.get(locale);
        if (resourceBundle == null) {
            resourceBundle = loadResourceBundleFromFile(locale);
        }
        return resourceBundle;
    }

    private ResourceBundle loadResourceBundleFromFile(Locale locale) {

        // load ResourceBundle from Filesystem
        File dir = new File(getDefaultPath());
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{dir.toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader loader = new URLClassLoader(urls);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASENAME, locale, loader);
        resourceBundles.put(locale, resourceBundle);
        return resourceBundle;
    }

    private boolean isResourceBundleUpToDate() {

        // first requested language code or already set to update
        if (toUpdate) {
            return !toUpdate;
        }

        // check resourcebundle not every time. only very 5 minute
        DateTime newTimeStamp = new DateTime();
        if (newTimeStamp.isAfter(timestamp)) { // never set before or timeout
            timestamp = newTimeStamp.plusMinutes(MINUTES);
            PortletPreferences portletPreferences = portletService.getPortletPreferences();
            toUpdate = Boolean.valueOf(portletPreferences.getValue("language_toupdate", StringPool.FALSE));
            try {
                portletPreferences.setValue("language_toupdate", StringPool.FALSE);
            } catch (ReadOnlyException e) {
                e.printStackTrace();
            }
            try {
                portletPreferences.store();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
            return !toUpdate; // update happened in flag
        }
        return true;
    }

    private synchronized void regenerateBundleFiles() {

        if (!toUpdate) { // method is synchronized - check again if necessary. probably it has already been happen
            return;
        }
        toUpdate = false;

        resourceBundles.clear();

        File dir = new File(getDefaultPath());
        boolean dirExists = true;
        if (!dir.exists()) {
            dirExists = dir.mkdirs();
        }
        if (dirExists) {
            for (Locale supportedLocale : LanguageUtil.getSupportedLocales()) {
                PortletPreferences portletPreferences = portletService.getPortletPreferences();
                String languageContent = portletPreferences.getValue("language_" + supportedLocale.toString(), StringPool.BLANK);
                String filename = BASENAME + "_" + supportedLocale.toString() + ".properties";
                try {
                    PrintWriter writer = new PrintWriter(dir.getAbsolutePath() + "/" + filename, "UTF-8");
                    writer.print(languageContent);
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDefaultPath() {
        return System.getProperty("catalina.home") + "/../config/" + portletService.getPortletConfig().getPortletName() + "/languages";
    }
}