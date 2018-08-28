package com.urise.model;

import java.util.*;

public class ExperienceSection implements Section {

    private Map<String, List<ExperienceEntry>> experienceMap;

    public ExperienceSection(List<ExperienceEntry> experienceEntries) {
        this.experienceMap = new HashMap<>();
        for (ExperienceEntry element : experienceEntries) {
            String name = element.getName().getName();
            if (experienceMap.containsKey(name)) {
                experienceMap.get(name).add(new Position(element.));
            }
            experienceMap.put(name, new Position(element.getTitle(), element.getStartDate(), element.getEndDate()));
        }
    }

//    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Map.Entry<String, Set<ExperienceEntry>> element : experienceMap.entrySet()) {
//            Set<ExperienceEntry> setExpEntry = element.getValue();
//            for (ExperienceEntry expEntry : setExpEntry) {
//                stringBuilder.append(element.getKey() + "\n"+ expEntry.getStartDate()+ "/" + expEntry.getEndDate() +
//                        "\t" + expEntry.getTitle() + "\n" +
//                        "\t\t\t\t" + expEntry.getDescription());
//            }
//            stringBuilder.append("\n");
//        }
//        return stringBuilder.toString();
//    }
}
