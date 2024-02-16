```
 private static List<String> getSpecialCharacterKeyReferencesDFS(
            Map<String, Object> nestedObject,
            String currentPath) {
        List<String> keyReferences = new ArrayList<>();
        for (Map.Entry<String, Object> entry : nestedObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String fullPath = currentPath.isEmpty() ? key : currentPath + "."
                    + key;
            if (value instanceof String)
                keyReferences.add(String.format("%s=%s", fullPath, value));

            if (value instanceof Map) {
                keyReferences.addAll(getSpecialCharacterKeyReferencesDFS(
                        (Map<String, Object>) value,
                        fullPath));
            }

            if (value instanceof List) {
                for (Map<String, Object> mapValue :
                        (List<Map<String, Object>>) value) {
                    keyReferences.addAll(getSpecialCharacterKeyReferencesDFS(
                            mapValue,
                            fullPath));
                }
            }
        }
        return keyReferences;
    }
```
