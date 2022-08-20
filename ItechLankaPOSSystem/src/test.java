import com.aspose.barcode.generation.BarcodeGenerator;
import com.aspose.barcode.generation.EncodeTypes;
import dao.Custom.OrderDAO;
import dao.Custom.impl.OrderDAOImpl;

import java.io.IOException;

public class test {
    private static final OrderDAO oDAO = new OrderDAOImpl();

    public static void main(String[] args) throws IOException {
        BarcodeGenerator generator = new BarcodeGenerator(EncodeTypes.CODE_128, "Derick Andrew Ferdinands");
// set resolution
        generator.getParameters().setResolution(400);
// generate barcode
        generator.save("generate-.png");
    }
}
