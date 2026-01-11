package com.sanal.omdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sanal.omdb.principal.Menus;

@SpringBootApplication
public class OmdbApplication {

    public static void main(String[] args) {
        ApplicationContext context =
            SpringApplication.run(OmdbApplication.class, args);

        Menus menus = context.getBean(Menus.class);
        menus.iniciarMenu();
    }
}

