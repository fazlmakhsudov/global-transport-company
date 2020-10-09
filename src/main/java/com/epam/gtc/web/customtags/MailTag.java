package com.epam.gtc.web.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class MailTag extends BodyTagSupport {
    private static final String MAIL_PATTERN = "(\\w{6,})@(\\w+\\.)(\\w{2,4})";

    @Override
    public int doAfterBody() throws JspException {
        BodyContent content = this.getBodyContent();
        String body = content.getString();
        String res = null;
        if (Pattern.matches(MAIL_PATTERN, body)) {
            res = body.replaceAll("\\.", "(dot)");
            res = res.replaceFirst("@", "(a)");
        } else {
            res = body + " is invalid e-mail";
        }
        JspWriter out = content.getEnclosingWriter();
        try {
            out.write(res);
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}