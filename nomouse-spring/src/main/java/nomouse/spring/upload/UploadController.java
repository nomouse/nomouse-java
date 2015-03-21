package nomouse.spring.upload;

import nomouse.spring.api.common.Res;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object handleUploadProcess(
            @RequestParam("imageFile") MultipartFile file) throws Exception {

        String path = "d:\\temp\\" + file.getOriginalFilename();

        File file1 = new File(path);

        FileUtils.writeByteArrayToFile(file1, file.getBytes());

        Res res = new Res();
        res.code = 200;
        return res;
    }
}

