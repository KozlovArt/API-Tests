package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListModel {
    List<DataInfo> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataInfo {
        Integer id;
        @JsonProperty("first_name")
        String firstName;

    }

}
