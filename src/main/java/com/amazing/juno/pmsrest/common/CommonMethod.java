package com.amazing.juno.pmsrest.common;

import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;

public class CommonMethod {

    public static String removeLastMatchingWord(String word, String text){
        int wordPointer = word.length() - 1;
        int textPointer = text.length() - 1;
        boolean firstMatchedFlag = false;
        int lastMatchedIndex = word.length() - 1;

        while(textPointer >= 0) {
            if(wordPointer == -1){
                break;
            }


            if(word.charAt(wordPointer) == text.charAt(textPointer)) {
                if (!firstMatchedFlag){
                    firstMatchedFlag = true;
                    lastMatchedIndex = textPointer;
                }
                wordPointer--;
            }


            textPointer--;
        }

        if(textPointer == -1){
            return text;
        }

        int firstMatchedIndex = textPointer + 1;

        return text.substring(0, firstMatchedIndex) + text.substring(lastMatchedIndex + 1);
    }


    public static String addUploadedDurationCondition(String resultWhereClause,
                                                LocalDateTime from,
                                                LocalDateTime to, String tableName) {
        if(from != null && to != null){
            resultWhereClause += String.format("AND %s.uploaded BETWEEN '%s' AND '%s'",tableName,from, to);
        } else if(from != null){
            resultWhereClause += String.format("AND %s.uploaded BETWEEN '%s' AND '%s'",tableName,from, LocalDateTime.now());
        }

        return resultWhereClause;
    }


}
