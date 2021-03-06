package com.github.mlk.guice.examples.legacy;

import com.github.mlk.guice.MagicalLegacyProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Key;

import static com.google.inject.name.Names.named;

public class Example {
    public static void main(String... argv) {
        Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(String.class).annotatedWith(named("host")).toInstance("example.com");
                        bind(int.class).annotatedWith(named("port")).toInstance(8080);
                        bind(LegacyAction.class).to(ModernAction.class);

                        bind(LegacyService.class).toProvider(new MagicalLegacyProvider<>(LegacyService.class,
                                Key.get(String.class, named("host")), Key.get(int.class, named("port")), Key.get(LegacyAction.class)));
                    }
                }).getInstance(LegacyService.class);
    }
}

