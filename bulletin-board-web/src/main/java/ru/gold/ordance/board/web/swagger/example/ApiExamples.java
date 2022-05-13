package ru.gold.ordance.board.web.swagger.example;

public final class ApiExamples {
    private ApiExamples() {
    }

    public static class Common {
        public static final String UPDATE_SUCCESS = "{\n" +
                "  \"EXAMPLES\": [\n" +
                "    {\n" +
                "      \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "      },\n" +
                "      \"entityId\": 1,\n" +
                "      \"updated\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "      },\n" +
                "      \"entityId\": null,\n" +
                "      \"updated\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        public static final String UPDATE_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null,\n" +
                "  \"updated\": false\n" +
                "}";

        public static final String UPDATE_VIOLATES_CONSTRAINT = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"VIOLATES_CONSTRAINT\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null,\n" +
                "  \"updated\": false\n" +
                "}";

        public static final String UPDATE_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null,\n" +
                "  \"updated\": false\n" +
                "}";

        public static final String DELETE_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  }\n" +
                "}";

        public static final String DELETE_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  }\n" +
                "}";

        public static final String DELETE_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  }\n" +
                "}";
    }

    public static class ApiAddress {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"addressList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"localityId\": 1,\n" +
                "      \"streetId\": 1,\n" +
                "      \"houseNumber\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"addressList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"addressList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiAdvertisement {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"advertisementList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"clientId\": 1,\n" +
                "      \"name\": \"string\",\n" +
                "      \"createDate\": \"2022-04-21\",\n" +
                "      \"subcategoryId\": 1,\n" +
                "      \"description\": \"string\",\n" +
                "      \"price\": 10000,\n" +
                "      \"localityId\": 1,\n" +
                "      \"streetId\": 1,\n" +
                "      \"houseNumber\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"advertisementList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"advertisementList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiCategory {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"categoryList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"categoryList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"categoryList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiClient {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"clientList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"login\": \"string\",\n" +
                "      \"name\": \"string\",\n" +
                "      \"phoneNumber\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"clientList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"clientList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String SAVE_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"entityId\": 1\n" +
                "}";

        public static final String SAVE_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null\n" +
                "}";

        public static final String SAVE_VIOLATES_CONSTRAINT = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"VIOLATES_CONSTRAINT\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null\n" +
                "}";

        public static final String SAVE_CALL_ERROR = "{{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"entityId\": null\n" +
                "}";
    }

    public static class ApiLnkLocalityStreet {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"lnkLocalityStreets\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"localityId\": 1,\n" +
                "      \"streetId\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"lnkLocalityStreets\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"lnkLocalityStreets\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiLocality {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"localityList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"name\": \"string\",\n" +
                "      \"regionId\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"localityList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"localityList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiRegion {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"regionList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"regionList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"regionList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiStreet {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"streetList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"streetList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"streetList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiSubcategory {
        public static final String FIND_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"subcategoryList\": [\n" +
                "    {\n" +
                "      \"entityId\": 1,\n" +
                "      \"name\": \"subcategory\",\n" +
                "      \"categoryId\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 1\n" +
                "}";

        public static final String FIND_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"subcategoryList\": null,\n" +
                "  \"total\": null\n" +
                "}";

        public static final String FIND_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"subcategoryList\": null,\n" +
                "  \"total\": null\n" +
                "}";
    }

    public static class ApiComplexAddress {
        public static final String COMPLEX_ADDRESS_SUCCESS = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "    },\n" +
                "    \"regionId\": 1,\n" +
                "    \"localityId\": 1,\n" +
                "    \"streetId\": 1\n" +
                "}";

        public static final String COMPLEX_ADDRESS_INVALID_RQ = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"INVALID_RQ\",\n" +
                "        \"description\": \"string\"\n" +
                "    },\n" +
                "    \"regionId\": null,\n" +
                "    \"localityId\": null,\n" +
                "    \"streetId\": null\n" +
                "}";

        public static final String COMPLEX_ADDRESS_CALL_ERROR = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"CALL_ERROR\",\n" +
                "        \"description\": \"string\"\n" +
                "    },\n" +
                "    \"regionId\": null,\n" +
                "    \"localityId\": null,\n" +
                "    \"streetId\": null\n" +
                "}";
    }

    public static class ApiAuth {
        public static final String REGISTRATION_SUCCESS = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "    }\n" +
                "}";

        public static final String REGISTRATION_INVALID_RQ = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"INVALID_RQ\",\n" +
                "        \"description\": \"string\"\n" +
                "    }\n" +
                "}";

        public static final String REGISTRATION_VIOLATES_CONSTRAINT = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"VIOLATES_CONSTRAINT\",\n" +
                "        \"description\": \"string\"\n" +
                "    }\n" +
                "}";

        public static final String REGISTRATION_CALL_ERROR = "{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"CALL_ERROR\",\n" +
                "        \"description\": \"string\"\n" +
                "    }\n" +
                "}";

        public static final String LOGIN_SUCCESS = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"description\": null\n" +
                "  },\n" +
                "  \"role\": \"string\"\n" +
                "  \"token\": \"string\"\n" +
                "}";

        public static final String LOGIN_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"role\": null,\n" +
                "  \"token\": null\n" +
                "}";

        public static final String LOGIN_UNAUTHORIZED = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"UNAUTHORIZED\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"role\": null,\n" +
                "  \"token\": null\n" +
                "}";

        public static final String LOGIN_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"role\": null,\n" +
                "  \"token\": null\n" +
                "}";

        public static final String TOKEN_SUCCESS = "{\n" +
                "  \"EXAMPLES\": [\n" +
                "    {\n" +
                "      \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "      },\n" +
                "      \"valid\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"status\": {\n" +
                "        \"code\": \"SUCCESS\",\n" +
                "        \"description\": null\n" +
                "      },\n" +
                "      \"valid\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        public static final String TOKEN_INVALID_RQ = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"INVALID_RQ\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"valid\": false\n" +
                "}";

        public static final String TOKEN_CALL_ERROR = "{\n" +
                "  \"status\": {\n" +
                "    \"code\": \"CALL_ERROR\",\n" +
                "    \"description\": \"string\"\n" +
                "  },\n" +
                "  \"valid\": false\n" +
                "}";
    }
}
