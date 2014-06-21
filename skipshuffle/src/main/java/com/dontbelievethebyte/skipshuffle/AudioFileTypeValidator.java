package com.dontbelievethebyte.skipshuffle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AudioFileTypeValidator {
    public static final boolean VALID = true;

    private Pattern pattern;
    private Matcher matcher;

    private static final String FILE_TYPES_PATTERN = ".*\\.(?:m(p(3|4)|4a|id|kv)|aac|flac|xmf|3gp|r(tttl|tx)|ota|imy|ogg|wav)$";

    public AudioFileTypeValidator(){
        pattern = Pattern.compile(FILE_TYPES_PATTERN);
    }

    /**
     * Validate image with regular expression
     * @param filename filename for validation
     * @return true valid extension, false invalid extension.
     */
    public boolean validate(final String filename){

        matcher = pattern.matcher(filename);
        return matcher.matches();

    }
}
