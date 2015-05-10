/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author Mary
 */
public class PageHelper {
     public static String PageLinks(int currentPage, int totalPages, String pageUrl)
        {
            String builder = new String();           
            //Prev 
            if (currentPage == 1)
            {
                
                builder = builder.concat("<li class=\"active\">" + "<a href=\"#\">&laquo;</a>" + "</li>");
            }
            else
            {
                builder = builder.concat("<li>" + "<a href=\"" + pageUrl+(currentPage - 1)+ "\">&laquo;</a>" + "</li>");
            }
            //По порядку
            for (int i = 1; i <= totalPages; i++)
            {
                //Условие что выводим только необходимые номера
                if (((i <= 3) || (i > (totalPages - 3))) || ((i > (currentPage - 2)) && (i < (currentPage + 2))))
                {
                    if (i == currentPage)
                    {
                        
                        builder = builder.concat("<li class=\"active\">" + "<a href=\"#\">" + i+ "</a>" + "</li>");
                    }
                    else
                    {
                        
                        builder = builder.concat("<li>" + "<a href=\"" + pageUrl+i+ "\">"+ i + "</a>" + "</li>");
                    }
                }
                else if ((i == 4) && (currentPage > 5))
                {
                    //Троеточие первое
                    builder = builder.concat("<li class=\"disabled\"> <a href=\"#\">...</a> </li>");
                }
                else if ((i == (totalPages - 3)) && (currentPage < (totalPages - 4)))
                {
                    //Троеточие второе
                    builder = builder.concat("<li class=\"disabled\"> <a href=\"#\">...</a> </li>");
                }
            }
            //Next
 
            if (currentPage == totalPages)
            {
                builder = builder.concat("<li class=\"active\">" + "<a href=\"#\">&raquo;</a>" + "</li>");
            }
            else
            {
                builder = builder.concat("<li>" + "<a href=\"" + pageUrl+(currentPage + 1)+ "\">&raquo;</a>" + "</li>");
            }
            builder = "<ul>" +builder + "</ul>";
            return builder;
        }
}
