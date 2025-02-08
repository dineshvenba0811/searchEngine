package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "activities")
@JsonIgnoreProperties(ignoreUnknown = true)
@Setting(settingPath = "elasticsearch-settings.json")
public class Activity {
    @Id
    private int id;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer", searchAnalyzer = "standard")
    private String title;
    private int price;
    private String currency;
    private Float rating;
    private boolean specialOffer;
    private int supplierId;
}
