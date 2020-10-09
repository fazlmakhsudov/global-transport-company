package com.epam.gtc.web.customtags;

import com.epam.gtc.web.models.RateModel;

import java.util.List;

public class ShowRatesFunction {
    public static String showRates(List<RateModel> rateModelList, int number) {
        StringBuilder sb = new StringBuilder();
        rateModelList.stream()
                .limit(number)
                .forEach(rate -> {
                    sb.append("<div class='col-sm-6 col-md-4 col-lg-3'>")
                            .append("<div class='card mb-4 shadow-sm' >")
                            .append(" <div class='card-header' >")
                            .append("<h4 class='my-0 font-weight-normal'>").append(rate.getName()).append("</h4>")
                            .append("</div >")
                            .append("<div class='card-body'>")
                            .append("<h1 class='card-title pricing-card-title' > $ ").append(rate.getCost())
                            .append("<small class='text-muted' >/<br / > request </small ></h1 >")
                            .append("<table class='table table-striped' >")
                            .append(" <tbody >")
                            .append("<tr >")
                            .append("<td >Max Weight</td>")
                            .append("<td>").append(rate.getMaxWeight()).append(" kg </td>")
                            .append("</tr >")
                            .append("<tr >")
                            .append("<td >Max Length</td>")
                            .append("<td>").append(rate.getMaxLength()).append(" cm </td>")
                            .append("</tr >")
                            .append("<tr >")
                            .append("<td >Max Width</td>")
                            .append("<td>").append(rate.getMaxWidth()).append(" cm </td>")
                            .append("</tr >")
                            .append("<tr >")
                            .append("<td >Max Height</td>")
                            .append("<td>").append(rate.getMaxHeight()).append(" cm </td>")
                            .append("</tr >")
                            .append("<tr >")
                            .append("<td >Max Distance</td>")
                            .append("<td>").append(rate.getMaxDistance()).append(" km </td>")
                            .append("</tr >")
                            .append("</tbody >")
                            .append("</table >")
                            .append("<a href = '/gtc/controller?command=rate&cost=10' >")
                            .append("<button type = 'button' class='btn btn-lg btn-block btn-outline-primary' >")
                            .append("Details")
                            .append("</button >")
                            .append("</a>")
                            .append("</div>")
                            .append("</div>")
                            .append("</div>");
                });
        return sb.toString();
    }
}
