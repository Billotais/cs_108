package ch.epfl.cs108;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TimestampReader {
    private TimestampReader() {}

    /**
     * Read the time stamps contained in the given resource file (one per line).
     *
     * @param resourceName
     *            the name of the resource containing the time stamps
     * @return a list of time stamps
     * @throws IOException
     *             if an IO error occurs (file not found, etc.)
     */
    public static List<LocalDateTime> readTimestamps(String... resourceName) throws IOException {
        List<LocalDateTime> timeStamps = new ArrayList<>();
        for (String r: resourceName)
            readTimestamps(r, timeStamps);
        return timeStamps;
    }

    private static void readTimestamps(String resourceName, List<LocalDateTime> targetList) throws IOException {
        try (BufferedReader r =
                new BufferedReader(
                        new InputStreamReader(
                                TimestampReader.class.getResourceAsStream(resourceName)))) {
            String line;
            while ((line = r.readLine()) != null)
                targetList.add(LocalDateTime.parse(line));
        }
    }
}
