package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@ExtendWith(MockitoExtension.class)
public class ActivityGraphQlTest {

    @Autowired
    private HttpGraphQlTester client;

    @Test
    void testGetAllActivities()  {
        var query = """
        query {
            getAllActivities {
                id
                title
                price
                currency
                rating
                specialOffer
                supplierId{
                              id
                              name
                              city
                            	address
                              country
                            }
            }
        }
        """;
        this.client.document(query)
                .execute()
                .path("getAllActivities").entityList(Object.class).hasSizeGreaterThan(1)
                .path("getAllActivities.[0].title").entity(String.class).isEqualTo("German Tour: Parliament Quarter & Reichstag glass dome")
                .path("getAllActivities.[0].price").entity(Integer.class).isEqualTo(14)
                .path("getAllActivities.[0].currency").entity(String.class).isEqualTo("$")
                .path("getAllActivities.[0].rating").entity(Float.class).isEqualTo(4.8F)
                .path("getAllActivities.[0].specialOffer").entity(Boolean.class).isEqualTo(false)
                .path("getAllActivities.[0].supplierId.id").entity(Integer.class).isEqualTo(1)
                .path("getAllActivities.[0].supplierId.name").entity(String.class).isEqualTo("John Doe")
                .path("getAllActivities.[0].supplierId.city").entity(String.class).isEqualTo("Anytown")
                .path("getAllActivities.[0].supplierId.address").entity(String.class).isEqualTo("123 Main St")
                .path("getAllActivities.[0].supplierId.country").entity(String.class).isEqualTo("USA");
    }

    @Test
    void testGetActivitiesByTitle()  {
        var query = """
                query {
                  getActivities(title:"berli",specialOffer:false){
                    id
                    title
                    currency
                    rating
                    specialOffer
                    supplierId{
                      id
                      name
                      address
                    }
                  }
                }
        """;
        this.client.document(query)
                .execute()
                .path("getActivities").entityList(Object.class).hasSizeGreaterThan(1)
                .path("getActivities.[0].title").entity(String.class).isEqualTo("Berlin WelcomeCard: Transport, Discounts & Guide Book");
    }

    @Test
    void testSearchAutoSuggest()  {
        var query = """
                query {
                          searchAutoSuggest(keyword: "ber"){
                            title
                            id
                          }
                        }
        """;
        this.client.document(query)
                .execute()
                .path("searchAutoSuggest").entityList(Object.class).hasSizeGreaterThan(1)
                .path("searchAutoSuggest.[0].title").entity(String.class).isEqualTo("Skip the Line: Pergamon Museum Berlin Tickets")
                .path("searchAutoSuggest.[0].id").entity(Integer.class).isEqualTo(6960);
    }

    @Test
    void testGetActivityById()  {
        var query = """
                query {
                          getActivityById(id:97470){
                            id
                           title
                            price
                            currency
                          }
                        }
        """;
        this.client.document(query)
                .execute()
                .path("getActivityById").entity(Object.class)
                .path("getActivityById.title").entity(String.class).isEqualTo("Berlin Hop-on Hop-off Bus Tour: 1- or 2-Day Ticket")
                .path("getActivityById.id").entity(Integer.class).isEqualTo(97470);
    }

}
