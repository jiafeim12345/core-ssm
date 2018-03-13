package me.cloudcat.develop.entity.vo;

import com.alibaba.fastjson.JSONArray;

public class DomainOutputVO {

    private Integer total;
    private JSONArray rows;

    public DomainOutputVO(Integer total, JSONArray rows) {
        total = total;
        this.rows = rows;
    }

    public DomainOutputVO() {}

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        total = total;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }
}
