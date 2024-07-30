package br.com.rafael_tech.screenMatchSprintBoot.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
