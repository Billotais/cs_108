package ch.epfl.cs108;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Main {

    public static void main(String[] args) throws IOException {
        List<LocalDateTime> ts = TimestampReader.readTimestamps("/stage-1.txt");
        StemPlot.print(hours(ts));
    }

    private static List<Integer> hours(List<LocalDateTime> timeStamps) {
        List<Integer> hours = new ArrayList<>();
        for (LocalDateTime t: timeStamps)
            hours.add(t.getHour() * 10 + (t.getMinute() / 10));
        return hours;
    }
}
