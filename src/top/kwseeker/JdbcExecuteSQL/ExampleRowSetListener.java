package top.kwseeker.JdbcExecuteSQL;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public class ExampleRowSetListener implements RowSetListener {

    // 内容改变时
    public void rowSetChanged(RowSetEvent event) {
        System.out.println("Called rowSetChanged in ExampleRowSetListener");
    }

    //
    public void rowChanged(RowSetEvent event) {
        System.out.println("Called rowChanged in ExampleRowSetListener");
    }

    // 指针移动时触发
    public void cursorMoved(RowSetEvent event) {
        System.out.println("Called cursorMoved in ExampleRowSetListener");
    }

}
