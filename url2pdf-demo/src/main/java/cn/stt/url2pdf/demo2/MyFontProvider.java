package cn.stt.url2pdf.demo2;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class MyFontProvider implements FontProvider {
    private BaseColor bc;
    private String fontname;
    private String encoding;
    private boolean embedded;
    private boolean cached;
    private float size;
    private int style;
    private BaseFont baseFont;

    public BaseColor getBc() {
        return bc;
    }

    public void setBc(BaseColor bc) {
        this.bc = bc;
    }

    public String getFontname() {
        return fontname;
    }

    public void setFontname(String fontname) {
        this.fontname = fontname;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public BaseFont getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    public MyFontProvider() {
    }

    public MyFontProvider(BaseColor bc, String fontname, String encoding, boolean embedded, boolean cached, float size, int style, BaseFont baseFont) {
        this.bc = bc;
        this.fontname = fontname;
        this.encoding = encoding;
        this.embedded = embedded;
        this.cached = cached;
        this.size = size;
        this.style = style;
        this.baseFont = baseFont;
    }

    @Override
    public boolean isRegistered(String s) {
//        return false;
        return true;
    }

    @Override
    public Font getFont(String s, String s1, boolean b, float v, int i, BaseColor baseColor) {
        Font font = null;
        if (baseFont == null) {
            font = new Font();
        } else {
            font = new Font(baseFont);
        }
        font.setColor(baseColor);
        font.setFamily(fontname);
        font.setSize(size);
        font.setStyle(i);
        return font;
    }
}
