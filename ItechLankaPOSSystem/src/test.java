import dao.Custom.ItemDAO;
import dao.Custom.OrderDAO;
import dao.Custom.impl.OrderDAOImpl;
import dao.DAOFactory;
import entity.Item;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class test {
    private static final OrderDAO oDAO = new OrderDAOImpl();

    public static void main(String[] args) throws Exception {


   /*     ItemDAO dao = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
        ArrayList<Item> all = dao.getAll();

        for (Item item : all) {
            EAN13Bean barcodeGenerator = new EAN13Bean();
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            String[] split = item.getCode().split("-");
            barcodeGenerator.generateBarcode(canvas, String.format("%012d",Integer.valueOf(split[1])));
            ImageIO.write(canvas.getBufferedImage(), "png", new File("bar-code.png"));
            File file = new File("bar-code.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] barCode = new byte[(int) file.length()];
            fileInputStream.read(barCode);

            dao.update(item);
        }*/
    }
}
