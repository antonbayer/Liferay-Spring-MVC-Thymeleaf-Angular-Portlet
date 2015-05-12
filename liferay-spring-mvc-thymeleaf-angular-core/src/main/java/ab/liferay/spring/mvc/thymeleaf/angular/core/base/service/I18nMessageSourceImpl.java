package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.portlet.util.PortletUtils;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@Service
public class I18nMessageSourceImpl extends AbstractMessageSource implements MessageSource {

    private static final int MINUTES = 5;
    private final PortletService portletService;
    private final Map<Locale, ResourceBundle> resourceBundles;
    private DateTime timestamp;
    private boolean toUpdate;

    @Autowired
    public I18nMessageSourceImpl(PortletService portletService) {
        this.portletService = portletService;
        resourceBundles = new HashMap<Locale, ResourceBundle>();
        timestamp = new DateTime().minusMinutes(MINUTES); // garantees file generation on first run
        toUpdate = true;
    }

    /**
     * get {@link MessageFormat} by code and locale
     * return .
     */
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {

        return new MessageFormat(getText(locale, code));
    }

    private String getText(Locale locale, String code) {

        ResourceBundle resourceBundle = getResourceBundle(locale);
        if (resourceBundle == null) {
            return getFallback(locale, code);
        }
        try {
            return resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            return getFallback(locale, code);
        }
    }

    private String getFallback(Locale locale, String code) {
        try { // fallback to the portlet language bundle in resources/content
            return LanguageUtil.get(portletService.getPortletConfig(), locale, code);
        } catch (MissingResourceException e) {
            return I18nMessageConstants.MISSING_PROPERTY_INDICATOR + code + StringPool.UNDERLINE + locale;
        }
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
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(I18nMessageConstants.BASENAME, locale, loader);
            resourceBundles.put(locale, resourceBundle);
        } catch (MissingResourceException e) {
        }
        return resourceBundle;
    }

    private boolean isResourceBundleUpToDate() {
        // check resourcebundle not every time. only very x minutes
        DateTime newTimeStamp = new DateTime();
        if (newTimeStamp.isAfter(timestamp)) { // never set before or timeout
            timestamp = newTimeStamp.plusMinutes(MINUTES);
            toUpdate = updateToUpdate();
            return !toUpdate; // update happened in flag
        }
        return true;
    }

    private synchronized boolean updateToUpdate() {
        PortletPreferences portletPreferences = portletService.getPortletPreferences();
        if (portletPreferences.getMap().size() == 0) {
            return false;
        }
        String pref = portletPreferences.getValue(I18nMessageConstants.CONFIGURATION_LANGUAGE_TO_UPDATE, StringPool.BLANK).toString();
        String key = getInstanceKey();
        if (!pref.contains(key)) {
            try {
                portletPreferences.setValue(I18nMessageConstants.CONFIGURATION_LANGUAGE_TO_UPDATE, pref + key);
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
            return true;
        }
        return false;
    }

    private String getInstanceKey() {
        return StringPool.PIPE + PortalUtil.getComputerAddress() + StringPool.EQUAL + PortalUtil.getComputerName() + StringPool.PIPE;
    }

    private synchronized void regenerateBundleFiles() {

        if (!toUpdate) { // method is synchronized - check again if necessary. probably it has already been happen
            return;
        }
        toUpdate = false;

        resourceBundles.clear();

        PortletPreferences portletPreferences = portletService.getPortletPreferences();
        if (portletPreferences == null) {
            return;
        }

        File dir = new File(getDefaultPath());
        boolean dirExists = true;
        if (!dir.exists()) {
            dirExists = dir.mkdirs();
        }
        if (dirExists) {
            for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
                String languageContent = portletPreferences.getValue(I18nMessageConstants.CONFIGURATION_LANGUAGE_PREFIX + availableLocale.toString(), StringPool.BLANK);
                String filename = I18nMessageConstants.BASENAME + StringPool.UNDERLINE + availableLocale.toString() + I18nMessageConstants.RESOURCE_BUNDLE_FILENAME_SUFFIX;
                try {
                    PrintWriter writer = null;
                    writer = new PrintWriter(dir.getAbsolutePath() + File.separator + filename, String.valueOf(StandardCharsets.ISO_8859_1));
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

    private String getDefaultPath() {
        return PortletUtils.getTempDir(portletService.getPortletContext()).getAbsolutePath() + I18nMessageConstants.RESOURCE_DEFAULT_PATH;
    }
}