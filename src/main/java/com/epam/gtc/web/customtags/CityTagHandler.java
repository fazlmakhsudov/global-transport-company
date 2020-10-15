package com.epam.gtc.web.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Generates html block for presenting city
 *
 * @author Fazliddin Makhsudov
 */
public class CityTagHandler extends TagSupport {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        StringBuilder sb = new StringBuilder();
        sb.append("<a class='item g1'>")
                .append("<img class='lazyOwl img-fluid' src='images/cities/")
                .append(name.toLowerCase())
                .append(".jpg' title='Transports' alt='No image' />")
                .append("<p class='slider-text text-white text-center'>").append(name).append("</p>")
                .append("</a>");

        try {
            out.print(sb.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return SKIP_BODY;
    }
}
