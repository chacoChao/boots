package com.chaco.chao.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * author:zhaopeiyan001
 * Date:2019-08-07 17:56
 */
@RestController
@RequestMapping("/random")
public class FakerController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("foods")
    public JsonNode foods() {
        Faker faker = new Faker(new Locale("en-US"));
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            arrayNode.add((objectMapper.createObjectNode()
                    .put("ingredients", faker.food().ingredient())
                    .put("spices", faker.food().spice())
                    .put("measurements", faker.food().measurement())
            ));
        }
        return arrayNode;
    }

    @GetMapping("persons")
    public JsonNode getRandomPersons() {
        Faker faker = new Faker();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            arrayNode.add((objectMapper.createObjectNode()
                    .put("firstName", faker.name().firstName())
                    .put("lastName", faker.name().lastName())
                    .put("title", faker.name().title())
                    .put("suffix", faker.name().suffix())
                    .put("address", faker.address().streetAddress())
                    .put("city", faker.address().cityName())
                    .put("country", faker.address().country())
            ));
        }
        return arrayNode;
    }

    @GetMapping("books")
    public JsonNode getRandomFoods() {
        Faker de = new Faker(new Locale("de"));
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            arrayNode.add(objectMapper.createObjectNode()
                    .put("autor", de.book().author())
                    .put("genre", de.book().genre())
                    .put("publish", de.book().publisher())
                    .put("title", de.book().title()));
        }
        return arrayNode;
    }

}
