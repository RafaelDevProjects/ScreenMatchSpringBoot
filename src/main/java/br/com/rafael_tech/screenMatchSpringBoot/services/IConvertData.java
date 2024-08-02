package br.com.rafael_tech.screenMatchSpringBoot.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
