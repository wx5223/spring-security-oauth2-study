package com.wx.controller;

import com.wx.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
public class IndexController {

    private RestService restService;
    @RequestMapping("/test")
	public String test() {
        RestOperations ro = restService.getRestTemplate();
        InputStream photosXML = new ByteArrayInputStream(ro.getForObject(
        URI.create("http://localhost:8080/server/photos"), byte[].class));
        final List<String> photoIds = new ArrayList<String>();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setValidating(false);
        parserFactory.setXIncludeAware(false);
        parserFactory.setNamespaceAware(false);
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(photosXML, new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if ("photo".equals(qName)) {
                        photoIds.add(attributes.getValue("id"));
                    }
                }
            });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("_____________________________________________");
        System.out.println(photoIds);
        System.out.println("_____________________________________________");
        return "test";
    }

    public RestService getRestService() {
        return restService;
    }
    @Resource
    public void setRestService(RestService restService) {
        this.restService = restService;
    }
}
