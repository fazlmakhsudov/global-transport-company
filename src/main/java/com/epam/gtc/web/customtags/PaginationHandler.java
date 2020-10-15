package com.epam.gtc.web.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Generates html block for pagination
 *
 * @author Fazliddin Makhsudov
 */
public class PaginationHandler extends TagSupport {
    private int allItems;
    private int itemsNumber;
    private int itemsPerPage;
    private int currentPage;
    private String url;
    private String itemName;
    private int pages;

    private void countPages() {
        pages = allItems / itemsPerPage;
        pages += (allItems % itemsPerPage) != 0 ? 1 : 0;
    }

    //TODO startRow -> page
    private String addParametersToUrl(int page) {
        return String.format("%s&page=%s&itemsPerPage=%s", url, page, itemsPerPage);
    }

    public int getAllItems() {
        return allItems;
    }


    public void setAllItems(int allItems) {
        this.allItems = allItems;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public int doStartTag() throws JspException {
        countPages();
        JspWriter out = pageContext.getOut();
        StringBuilder sb = new StringBuilder();
        sb.append("<!-- Pagination -->")
                .append(" <div class=''>")
                .append("<nav aria-label='...'>")
                .append("<ul class='pagination justify-content-center'>")
                .append("<li class='page-item").append(currentPage <= 1 ? " disabled'>" : "'>")
                .append("<a class='page-link").append(currentPage > 1 ? " active' href='" : " ' href='")
                .append(addParametersToUrl(currentPage - 1))
                .append("' data-page='")
                .append(currentPage - 1)
                .append("'>Previous</a>")
                .append("</li>")
                .append("<li class='page-item").append(currentPage == 1 ? " active'>" : "'>")
                .append("<a class='page-link' href='")
                .append(addParametersToUrl(1))
                .append("' data-page='")
                .append(1)
                .append("'>")
                .append(1)
                .append("</a>");

        for (int i = 2; i <= pages; i++) {
            sb.append("</li >")
                    .append("<li class='page-item")
                    .append(currentPage == i ? " active " : "")
                    .append("'>")
                    .append("<a class='page-link' href='")
                    .append(addParametersToUrl(i))
                    .append("' data-page='").append(i).append("'>")
                    .append(i)
                    .append("</a>");
        }
        sb.append("</li>")
                .append("<li class='page-item").append(currentPage >= pages ? " disabled'>" : "'>")
                .append("<a class='page-link").append(currentPage < pages ? " active' href='" : " ' href='")
                .append(addParametersToUrl(currentPage + 1))
                .append("' data-page='")
                .append(currentPage + 1)
                .append("'>Next</a></li>")
                .append("</ul>")
                .append("</nav>")
                .append("<p class = 'd-inline ml-5'>")
                .append("<ins class = 'h6'>").append(itemsNumber).append("</ins> ")
                .append(itemName)
                .append(" from <p class='h6 d-inline font-weight-bold'>")
                .append(allItems)
                .append("</p></p>")
                .append("</div>")
                .append("<!-- End of Pagination -->");

        try {
            out.write(sb.toString());
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}