package ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.AllController;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


@AllController
public class StaticContentController {

    private PortletService portletService;

    @Autowired
    public StaticContentController(PortletService portletService) {
        this.portletService = portletService;
    }

    @ResourceMapping(value = Constants.STATIC_CONTENT_RESOURCE_URL)
    @RequestMapping(method = RequestMethod.GET, params = Constants.STATIC_CONTENT_FILE_PARAM)
    public void file() {

        String directory = Constants.STATIC_CONTENT_RESOURCE_URL;
        String filename = portletService.getResourceRequest().getParameter(Constants.STATIC_CONTENT_FILE_PARAM);
        Path path = FileSystems.getDefault().getPath(directory, filename);
        String contenttype = null;
        try {
            contenttype = Files.probeContentType(path);
            portletService.getResourceResponse().setContentType(contenttype);

            OutputStream os = portletService.getResourceResponse().getPortletOutputStream();
            InputStream is = portletService.getPortletConfig().getPortletContext().getResourceAsStream(path.toString());

            int n;
            byte[] buffer = new byte[1024];
            while ((n = is.read(buffer)) > -1) {
                os.write(buffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }

            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}