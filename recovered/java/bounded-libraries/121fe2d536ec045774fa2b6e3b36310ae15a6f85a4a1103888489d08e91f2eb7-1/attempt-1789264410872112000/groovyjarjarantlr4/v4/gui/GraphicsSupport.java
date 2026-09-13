/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JComponent;

public class GraphicsSupport {
    public static void saveImage(final JComponent comp, String fileName) throws IOException, PrintException {
        if (fileName.endsWith(".ps") || fileName.endsWith(".eps")) {
            DocFlavor.SERVICE_FORMATTED flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            String mimeType = "application/postscript";
            Object[] factories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor, mimeType);
            System.out.println(Arrays.toString(factories));
            if (factories.length > 0) {
                FileOutputStream out = new FileOutputStream(fileName);
                StreamPrintService service = ((StreamPrintServiceFactory)factories[0]).getPrintService(out);
                SimpleDoc doc = new SimpleDoc(new Printable(){

                    @Override
                    public int print(Graphics g, PageFormat pf, int page) {
                        if (page >= 1) {
                            return 1;
                        }
                        Graphics2D g2 = (Graphics2D)g;
                        g2.translate((pf.getWidth() - pf.getImageableWidth()) / 2.0, (pf.getHeight() - pf.getImageableHeight()) / 2.0);
                        if ((double)comp.getWidth() > pf.getImageableWidth() || (double)comp.getHeight() > pf.getImageableHeight()) {
                            double sf1 = pf.getImageableWidth() / (double)(comp.getWidth() + 1);
                            double sf2 = pf.getImageableHeight() / (double)(comp.getHeight() + 1);
                            double s = Math.min(sf1, sf2);
                            g2.scale(s, s);
                        }
                        comp.paint(g);
                        return 0;
                    }
                }, flavor, null);
                DocPrintJob job = service.createPrintJob();
                HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
                job.print(doc, attributes);
                out.close();
            }
        } else {
            Rectangle rect = comp.getBounds();
            BufferedImage image = new BufferedImage(rect.width, rect.height, 1);
            Graphics2D g = (Graphics2D)image.getGraphics();
            g.setColor(Color.WHITE);
            g.fill(rect);
            comp.paint(g);
            String extension = fileName.substring(fileName.lastIndexOf(46) + 1);
            boolean result = ImageIO.write((RenderedImage)image, extension, new File(fileName));
            if (!result) {
                System.err.println("Now imager for " + extension);
            }
            g.dispose();
        }
    }
}

