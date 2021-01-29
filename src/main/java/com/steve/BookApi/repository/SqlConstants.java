package com.steve.BookApi.repository;

public class SqlConstants {
    public static final String id = "prim_cat_tree_id";
    public static final String name = "cat_name_tx";
    public static final String user = "audt_cr_user_cd";
    public static final String time = "audt_upd_dt_tm";

    public static final String getAllBooksQuery = String.format(
            "SELECT %s, %s, cat.%s, cat.%s FROM omt_prim_cat_tree tree " +
                    "JOIN omt_prim_cat cat ON tree.chld_cat_id = cat.prim_cat_id " +
                    "WHERE prim_cat_tree_id IN (:id, :id2)", id, name, user, time);
}
