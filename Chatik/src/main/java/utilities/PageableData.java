/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.List;

/**
 *
 * @author Mary
 * @param <T>
 */
public class PageableData<T> {
    
        protected static int ItemPerPageDefault = 20;

        private List<T> list;

        private int PageNo;

        private int CountPage;

        private int ItemPerPage;

        public PageableData(List<T> queryableSet, int page, int itemPerPage)
        {
            if (itemPerPage == 0)
            {
                itemPerPage = ItemPerPageDefault;
            }
            ItemPerPage = itemPerPage;

            PageNo = page;
            int count = queryableSet.size();

            CountPage = (count%itemPerPage) == 0 ? count / itemPerPage : count / itemPerPage + 1;
            if(PageNo * itemPerPage < count )
            {
                list = queryableSet.subList((PageNo - 1) * itemPerPage,PageNo*itemPerPage);
            }
            else
            {
                list = queryableSet.subList((PageNo - 1) * itemPerPage, count);
            }
        }
    /**
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return the PageNo
     */
    public int getPageNo() {
        return PageNo;
    }

    /**
     * @param PageNo the PageNo to set
     */
    public void setPageNo(int PageNo) {
        this.PageNo = PageNo;
    }

    /**
     * @return the CountPage
     */
    public int getCountPage() {
        return CountPage;
    }

    /**
     * @param CountPage the CountPage to set
     */
    public void setCountPage(int CountPage) {
        this.CountPage = CountPage;
    }

    /**
     * @return the ItemPerPage
     */
    public int getItemPerPage() {
        return ItemPerPage;
    }

    /**
     * @param ItemPerPage the ItemPerPage to set
     */
    public void setItemPerPage(int ItemPerPage) {
        this.ItemPerPage = ItemPerPage;
    }
    
}
