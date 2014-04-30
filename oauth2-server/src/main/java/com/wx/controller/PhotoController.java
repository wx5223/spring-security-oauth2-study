package com.wx.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
public class PhotoController {


	@RequestMapping(value = "/photos")
	public ResponseEntity<String> getXmlPhotos(Principal principal) {

		StringBuilder out = new StringBuilder();
		out.append("<photos>");
		for (int i : new Integer[]{1,2,3}) {
			out.append(String.format("<photo id=\"%s\" name=\"%s\"/>", i, "name"+i));
		}
		out.append("</photos>");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/xml");
		return new ResponseEntity<String>(out.toString(), headers, HttpStatus.OK);
	}

	@RequestMapping("/photos/trusted/message")
	@PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT')")
	@ResponseBody
	public String getTrustedClientMessage() {
		return "Hello, Trusted Client";
	}

	@RequestMapping("/photos/user/message")
	@ResponseBody
	public String getTrustedUserMessage(Principal principal) {
		return "Hello, Trusted User" + (principal!=null ? " " + principal.getName() : "");
	}


}
