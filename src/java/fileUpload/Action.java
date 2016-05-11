package fileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.simple.JSONObject;

public class Action extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		File dir = ((File) request.getSession().getAttribute("dir"));
		if (dir != null) {
			ServletFileUpload upload = new ServletFileUpload();
			try {
				FileItemIterator iterator = upload.getItemIterator(request);
				if (iterator.hasNext()) {
					FileItemStream stream = iterator.next();
					if (!stream.isFormField()) {
						if (isSuitableExtension(stream.getContentType())) {
							int code = ((Integer) request.getSession().getAttribute("code")).intValue();
							String ext = getSuitableExtension(stream.getContentType());
							long length = Streams.copy(stream.openStream(), new FileOutputStream(dir.getAbsolutePath() + File.separator + "img" + code + ""+ext), true);
							json.put("status", 1);
							json.put("message", "File uploaded successfully!");
						} else {
							json.put("status", 0);
							json.put("message", "Invalid file format!");
						}
					} else {
						json.put("status", 0);
						json.put("message", "File not received!");
					}
				} else {
					json.put("status", 0);
					json.put("message", "File not received!");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				json.put("status", 0);
				json.put("message", "Internal server error!");
			}
		} else {
			json.put("status", 0);
			json.put("message", "File upload failed!");
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	boolean isSuitableExtension(String type) {
		return (type.equals("image/png") || type.equals("image/jpg") || type.equals("image/jpeg"));
	}

	String getSuitableExtension(String type) {
		switch (type) {
			case "image/png":
				return ".png";
			case "image/jpg":
				return ".jpg";
			case "image/jpeg":
				return ".jpeg";
			default:
				return "";
		}
	}
}
