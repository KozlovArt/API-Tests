package models;

import lombok.Data;

@Data
public class UserFullInfoModel {
    Support support;
    DataInfo data;

    @Data
    public static class DataInfo {
        Integer id;
        String email, first_name, last_name, avatar;
    }

    @Data
    public static class Support {
        String url, text;
    }
}
