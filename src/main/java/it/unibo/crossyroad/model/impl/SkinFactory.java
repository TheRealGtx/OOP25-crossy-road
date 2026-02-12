package it.unibo.crossyroad.model.impl;

import java.nio.file.Path;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import it.unibo.crossyroad.model.api.Skin;

/**
 * Factory for creating a skin from a source.
 */
public final class SkinFactory {

    /**
     * Contructor of utility class.
     */
    private SkinFactory() {

    }

    /**
     * Create a skin from a json node.
     * 
     * @param node the json node.
     * @return a new skin.
     */
    public static Skin loadFromJson(final JsonNode node) {
        return Optional.ofNullable(node)
            .filter(SkinFactory::hasRequiredFields)
            .map(SkinFactory::buildSkinFromNode)
            .orElseThrow(() -> new IllegalArgumentException(
                "Problems with file json"
            ));
    }

    /**
     * Create the skin.
     * 
     * @param name the name of the skin.
     * @param id the id of the skin.
     * @param price the price of the skin.
     * @param overheadImagePath the overhead image path.
     * @param frontImagePath the front image path.
     * @return a new skin.
     */
    public static Skin create(
            final String name,
            final String id,
            final int price,
            final Path overheadImagePath,
            final Path frontImagePath) {
        return new SkinImpl.Builder()
                .name(name)
                .id(id)
                .price(price)
                .overheadImagePath(overheadImagePath)
                .frontImagePath(frontImagePath)
                .build();
    }

    /**
     * Control if the json has the required fields.
     * 
     * @param node the json node.
     * @return true if the json has all required fields, false otherwise.
     */
    private static boolean hasRequiredFields(final JsonNode node) {
        return node.has("name")
            && node.has("id")
            && node.has("price")
            && node.has("overheadImage")
            && node.has("frontalImage");
    }

    /**
     * Build a skin instance from the json node.
     * 
     * @param node the json node.
     * @return new skin instance.
     */
    private static Skin buildSkinFromNode(final JsonNode node) {
        return new SkinImpl.Builder()
            .name(getTextValue(node, "name"))
            .id(getTextValue(node, "id"))
            .price(getIntValue(node, "price"))
            .overheadImagePath(getPathValue(node, "overheadImage"))
            .frontImagePath(getPathValue(node, "frontalImage"))
            .build();
    }

    /**
     * Take a text value from json node.
     * 
     * @param node the json node.
     * @param field the field name to take.
     * @return the text value.
     */
    private static String getTextValue(final JsonNode node, final String field) {
        return Optional.ofNullable(node.get(field))
            .map(JsonNode::asText)
            .filter(text -> !text.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("missing field: " + field));
    }

    /**
     * Take a int value from json node.
     * 
     * @param node the json node.
     * @param field the field name to take.
     * @return the int value.
     */
    private static Integer getIntValue(final JsonNode node, final String field) {
        return Optional.ofNullable(node.get(field))
            .map(JsonNode::asInt)
            .orElseThrow(() -> new IllegalArgumentException("field: " + field + "is missing"));
    }

    /**
     * Take the path value from json node.
     * 
     * @param node the json node.
     * @param field the field name to take.
     * @return the int value.
     */
    private static Path getPathValue(final JsonNode node, final String field) {
        return Optional.ofNullable(node.get(field))
            .map(JsonNode::asText)
            .filter(text -> !text.isEmpty())
            .map(Path::of)
            .orElseThrow(() -> new IllegalArgumentException("missing: " + field));
    }
}
