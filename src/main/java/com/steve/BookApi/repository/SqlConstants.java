package com.steve.BookApi.repository;

public class SqlConstants {
    public static final String id = "prim_cat_tree_id";
    public static final String name = "cat_name_tx";
    public static final String user = "audt_cr_user_cd";
    public static final String time = "audt_upd_dt_tm";

    public static final String getAllBooksQuery = String.format(
            "SELECT * from book a " +
                    "INNER JOIN author b " +
                        "ON a.authorId = b.authorId " +
                    "INNER JOIN genre c " +
                        "On a.genreId = c.genreId ");
}
