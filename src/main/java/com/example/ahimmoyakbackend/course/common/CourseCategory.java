package com.example.ahimmoyakbackend.course.common;

import lombok.Getter;

@Getter
public enum CourseCategory {
    ALL(1L, "전체"),
    BUSINESS_MANAGEMENT(2L, "사업관리"),
    MANAGEMENT_ACCOUNTING_ADMINISTRATIVE_AFFAIRS(3L, "경영·회계·사무"),
    FINANCE_INSURANCE(4L, "금융·보험"),
    EDUCATION_NATURE_EDUCATION_NATURE_SOCIAL_SCIENCE(5L, "교육·자연·사회과학"),
    LAW_POLICE_FIRE_RELIGION_DEFENSE(6L, "법률·경찰·소방·교도·국방"),
    HEALTH_MEDICAL_CARE(7L, "보건·의료"),
    SOCIAL_WELFARE_RELIGION(8L, "사회복지·종교"),
    CULTURE_ART_DESIGN_BROADCASTING(9L, "문화·예술·디자인·방송"),
    DRIVING_TRANSPORTATION(10L, "운전·운송"),
    BUSINESS_SALES(11L, "영업판매"),
    SECURITY_CLEANING(12L, "경비·청소"),
    UTILIZING_ACCOMMODATION_TRAVEL_ENTERTAINMENT_SPORTS(13L, "이용·숙박·여행·오락·스포츠"),
    FOOD_SERVICE(14L, "음식서비스"),
    CONSTRUCTION(15L, "건설"),
    MACHINERY(16L, "기계"),
    MATERIALS(17L, "재료"),
    CHEMICAL_BIO(18L, "화학·바이오"),
    TEXTILE_CLOTHING(19L, "섬유·의복"),
    ELECTRICAL_ELECTRONIC(20L, "전기·전자"),
    INFORMATION_COMMUNICATION(21L, "정보통신"),
    FOOD_PROCESSING(22L, "식품가공"),
    PRINTED_WOOD_FURNITURE_CRAFTS(23L, "인쇄·목재·가구·공예"),
    ENVIRONMENTAL_ENERGY_SAFETY(24L, "환경·에너지·안전"),
    AGRICULTURE_FORESTRY_FISHERIES(25L, "농림어업");

    private final Long categoryNumber;
    private final String title;

    CourseCategory(Long categoryNumber, String title) {
        this.categoryNumber = categoryNumber;
        this.title = title;
    }
}
