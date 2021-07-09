package org.sample.canvashell.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sample.canvashell.domain.command.validation.ValidHorizontalOrVerticalLine;
import org.sample.canvashell.domain.command.validation.ValidPointsInCanvas;
import org.sample.canvashell.domain.model.Canvas;
import org.sample.canvashell.domain.model.Pixel;
import org.sample.canvashell.domain.model.Point;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@ValidPointsInCanvas(message = "One or more points are outside the canvas")
@ValidHorizontalOrVerticalLine(message = "Only vertical and horizontal lines are supported")
public class DrawLineCommand extends Command implements CanvasAndPoints {
    @NotNull(message = "Canvas has to be initialized first")
    private final Canvas canvas;
    private final Point from;
    private final Point to;

    private static final char lineColor = 'x';
    private static final Pixel lineFill = new Pixel(lineColor);

    @Override
    public void execute() {
        int closeCoord;
        int farCoord;
        int fixedCoord;

        if (from.getX() == to.getX()) {
            closeCoord = Math.min(from.getY(), to.getY());
            farCoord = Math.max(from.getY(), to.getY());
            fixedCoord = from.getX();
            for (int i = closeCoord; i <= farCoord; i++) {
                canvas.setPixel(fixedCoord, i, lineFill);
            }
        } else {
            closeCoord = Math.min(from.getX(), to.getX());
            farCoord = Math.max(from.getX(), to.getX());
            fixedCoord = from.getY();
            for (int i = closeCoord; i <= farCoord; i++) {
                canvas.setPixel(i, fixedCoord, lineFill);
            }
        }
    }

    @Override
    public Collection<Point> getPoints() {
        return Arrays.asList(from, to);
    }
}
