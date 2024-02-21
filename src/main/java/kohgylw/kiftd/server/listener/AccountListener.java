package kohgylw.kiftd.server.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import kohgylw.kiftd.server.model.Account;
import kohgylw.kiftd.server.service.FolderService;
import kohgylw.kiftd.server.util.ConfigureReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AccountListener implements ReadListener<Account> {

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param folderService
     */
    public AccountListener(HttpServletRequest request, FolderService folderService) {
        this.request = request;
        this.fs = folderService;
    }

    @Override
    public void invoke(Account account, AnalysisContext analysisContext) {
        System.out.println("invoke解析到一条数据:" + account);
        try {
            ConfigureReader.instance().updateAccount(account);
            if(!StringUtils.isEmpty(account.getFolder())){
                request.setAttribute("parentId", "root");
                request.setAttribute("folderName", account.getFolder());
                request.setAttribute("folderConstraint", "0");
                this.fs.newFolder(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成！");
    }

    private FolderService fs;

    private HttpServletRequest request;
}
