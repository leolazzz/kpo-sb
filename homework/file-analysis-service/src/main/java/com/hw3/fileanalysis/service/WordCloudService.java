package com.hw3.fileanalysis.service;

import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class WordCloudService {

    public String generateWordCloud(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "https://quickchart.io/wordcloud?text=No+content+available&width=800&height=400";
        }

        String cleanText = text.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ");

        String[] words = cleanText.split("\\s+");

        List<String> stopWords = Arrays.asList(
                "the", "and", "to", "of", "a", "in", "that", "is", "was", "he",
                "for", "it", "with", "as", "his", "on", "be", "at", "by", "i",
                "this", "had", "not", "are", "but", "from", "or", "have", "an",
                "they", "which", "one", "you", "were", "her", "all", "she", "there",
                "would", "their", "we", "him", "been", "has", "when", "who", "will",
                "more", "no", "out", "do", "so", "what", "up", "its", "about", "into",
                "than", "them", "can", "only", "other", "new", "some", "could", "time",
                "these", "two", "may", "then", "now", "like", "my", "me", "made", "did",
                "must", "also", "first", "any", "see", "such", "our", "over", "know",
                "very", "after", "most", "should", "where", "just", "well", "how",
                "even", "because", "get", "back", "much", "go", "through", "come",
                "here", "think", "before", "too", "way", "still", "take", "say", "same",
                "another", "never", "little", "work", "might", "last", "need", "many",
                "those", "each", "right", "why", "down", "off", "only", "old", "any",
                "also", "very", "well", "too", "such", "just", "much", "about"
        );

        Map<String, Integer> wordFreq = new HashMap<>();
        for (String word : words) {
            if (word.length() > 2 && !stopWords.contains(word)) {
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFreq.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        StringBuilder wordCloudText = new StringBuilder();
        int limit = Math.min(30, sortedEntries.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = sortedEntries.get(i);
            String word = entry.getKey();
            int weight = Math.min(entry.getValue(), 10);

            for (int j = 0; j < weight; j++) {
                wordCloudText.append(word).append(" ");
            }
        }

        if (wordCloudText.length() == 0) {
            wordCloudText.append("text work document sample content");
        }

        String encodedText = URLEncoder.encode(wordCloudText.toString().trim(), StandardCharsets.UTF_8);

        return String.format(
                "https://quickchart.io/wordcloud?text=%s&width=800&height=400&format=png" +
                        "&fontFamily=Arial&backgroundColor=white&colors=#1f77b4,#ff7f0e,#2ca02c,#d62728",
                encodedText
        );
    }
}