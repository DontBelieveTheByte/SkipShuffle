package com.dontbelievethebyte.skipshuffle.activities.util;

public class MimeTypes {

    public static final String _RegexFileTypePlainTexts = "(?si).+\\.(txt|html?|json|csv|java|pas|php.+|c|cpp|"
            + "bas|python|js|javascript|scala|xml|kml|css|ps|xslt?|tpl|tsv|bash|cmd|pl|pm|ps1|ps1xml|psc1|psd1|psm1|"
            + "py|pyc|pyo|r|rb|sdl|sh|tcl|vbs|xpl|ada|adb|ads|clj|cls|cob|cbl|cxx|cs|csproj|d|e|el|go|h|hpp|hxx|l|"
            + "m)";

    public static final String _RegexFileTypeImages = "(?si).+\\.(gif|jpe?g|png|tiff?|wmf|emf|jfif|exif|"
            + "raw|bmp|ppm|pgm|pbm|pnm|webp|riff|tga|ilbm|img|pcx|ecw|sid|cd5|fits|pgf|xcf|svg|pns|jps|icon?|"
            + "jp2|mng|xpm|djvu)";

    public static final String _RegexFileTypeAudios = "(?si).+\\.(mp[2-3]+|wav|aiff|au|m4a|ogg|raw|flac|"
            + "mid|amr|aac|alac|atrac|awb|m4p|mmf|mpc|ra|rm|tta|vox|wma)";

    public static final String _RegexFileTypeVideos = "(?si).+\\.(mp[4]+|flv|wmv|webm|m4v|3gp|mkv|mov|mpe?g|rmv?|ogv)";

    public static final String _RegexFileTypeCompressed = "(?si).+\\.(zip|7z|lz?|[jrt]ar|gz|gzip|bzip|xz|cab|sfx|"
            + "z|iso|bz?|rz|s7z|apk|dmg)";
}
