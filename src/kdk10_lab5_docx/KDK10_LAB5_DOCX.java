package kdk10_lab5_docx;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class KDK10_LAB5_DOCX {

    public static void main(String[] args) throws Exception {
        String dir = new File(".").getAbsoluteFile().getParentFile().getAbsolutePath()
                + System.getProperty("file.separator");
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(dir + "input.docx"));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.toLowerCase().contains("changes")) {
                        text = text.replace(text, "New Info");
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.toLowerCase().contains("changes")) {
                                text = text.replace(text, "New Table Info");
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
        FileOutputStream fos = new FileOutputStream(dir + "output.docx");
        doc.write(fos);
        fos.close();
        
        
        Document document = new Document();
        document.loadFromFile("output.docx");

        //Сохранить как PDF
        document.saveToFile("outed.pdf", FileFormat.PDF);
    }

}
