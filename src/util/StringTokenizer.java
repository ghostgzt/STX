package util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class StringTokenizer
        implements Enumeration {

    private int charCount(int ch) {
        return ch < 0x10000 ? 1 : 2;
    }

    private void setMaxDelimCodePoint() {
        if (delimiters == null) {
            maxDelimCodePoint = 0;
            return;
        }
        int m = 0;
        int count = 0;
        int c;
        for (int i = 0; i < delimiters.length(); i += charCount(c)) {
            c = delimiters.charAt(i);
            if (c >= 55296 && c <= 56319) {
                c = delimiters.charAt(i);
                hasSurrogates = true;
            }
            if (m < c) {
                m = c;
            }
            count++;
        }

        maxDelimCodePoint = m;
        if (hasSurrogates) {
            delimiterCodePoints = new int[count];
            int i = 0;

            for (int j = 0; i < count; j += charCount(c)) {
                c = delimiters.charAt(j);
                delimiterCodePoints[i] = c;
                i++;
            }

        }
    }

    public StringTokenizer(String str, String delim, boolean returnDelims) {
        hasSurrogates = false;
        currentPosition = 0;
        newPosition = -1;
        delimsChanged = false;
        this.str = str;
        maxPosition = str.length();
        delimiters = delim;
        retDelims = returnDelims;
        setMaxDelimCodePoint();
    }

    public StringTokenizer(String str, String delim) {
        this(str, delim, false);
    }

    public StringTokenizer(String str) {
        this(str, " \t\n\r\f", false);
    }

    private int skipDelimiters(int startPos) {
        if (delimiters == null) {
            throw new NullPointerException();
        }
        int position = startPos;
        while (!retDelims && position < maxPosition) {
            int c;
            if (!hasSurrogates) {
                c = str.charAt(position);
                if (c > maxDelimCodePoint || delimiters.indexOf(c) < 0) {
                    break;
                }
                position++;
                continue;
            }
            c = str.charAt(position);
            if (c > maxDelimCodePoint || !isDelimiter(c)) {
                break;
            }
            position += charCount(c);
        }
        return position;
    }

    private int scanToken(int startPos) {
        int position = startPos;
        while (position < maxPosition) {
            int c;
            if (!hasSurrogates) {
                c = str.charAt(position);
                if (c <= maxDelimCodePoint && delimiters.indexOf(c) >= 0) {
                    break;
                }
                position++;
                continue;
            }
            c = str.charAt(position);
            if (c <= maxDelimCodePoint && isDelimiter(c)) {
                break;
            }
            position += charCount(c);
        }
        if (retDelims && startPos == position) {
            if (!hasSurrogates) {
                char c = str.charAt(position);
                if (c <= maxDelimCodePoint && delimiters.indexOf(c) >= 0) {
                    position++;
                }
            } else {
                int c = str.charAt(position);
                if (c <= maxDelimCodePoint && isDelimiter(c)) {
                    position += charCount(c);
                }
            }
        }
        return position;
    }

    private boolean isDelimiter(int codePoint) {
        for (int i = 0; i < delimiterCodePoints.length; i++) {
            if (delimiterCodePoints[i] == codePoint) {
                return true;
            }
        }

        return false;
    }

    public boolean hasMoreTokens() {
        newPosition = skipDelimiters(currentPosition);
        return newPosition < maxPosition;
    }

    public String nextToken() {
        currentPosition = newPosition < 0 || delimsChanged ? skipDelimiters(currentPosition) : newPosition;
        delimsChanged = false;
        newPosition = -1;
        if (currentPosition >= maxPosition) {
            throw new NoSuchElementException();
        } else {
            int start = currentPosition;
            currentPosition = scanToken(currentPosition);
            return str.substring(start, currentPosition);
        }
    }

    public String nextToken(String delim) {
        delimiters = delim;
        delimsChanged = true;
        setMaxDelimCodePoint();
        return nextToken();
    }

    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    public Object nextElement() {
        return nextToken();
    }

    public int countTokens() {
        int count = 0;
        for (int currpos = currentPosition; currpos < maxPosition;) {
            currpos = skipDelimiters(currpos);
            if (currpos >= maxPosition) {
                break;
            }
            currpos = scanToken(currpos);
            count++;
        }

        return count;
    }
    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private String str;
    private String delimiters;
    private boolean retDelims;
    private boolean delimsChanged;
    private int maxDelimCodePoint;
    private boolean hasSurrogates;
    private int delimiterCodePoints[];
}
