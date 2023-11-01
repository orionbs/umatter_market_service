package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobDatabaseEntity {
    private List<String> domains = new ArrayList<>();
    private Type type;
    private Integer yearOfExperience;
    private List<ItemDatabaseEntity> skills = new ArrayList<>();
    private List<ItemDatabaseEntity> tools = new ArrayList<>();
    private List<ItemDatabaseEntity> tasks = new ArrayList<>();

    public enum Type {
        JUNIOR,
        MEDIOR,
        SENIOR
    }
}
