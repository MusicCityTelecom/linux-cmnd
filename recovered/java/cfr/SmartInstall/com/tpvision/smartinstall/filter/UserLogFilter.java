/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.WriteListener
 */
package com.tpvision.smartinstall.filter;

import com.tpvision.smartinstall.util.DownloadLimiter;
import com.tpvision.smartinstall.util.Log4j2Utils;
import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.util.FormatUtil;

public class UserLogFilter
implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(UserLogFilter.class);

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        LOG.info("UserLogFilter init ...");
    }

    @Override
    public void destroy() {
        LOG.info("UserLogFilter destroy ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isDownloadUpgradePackage;
        HttpServletRequest req = (HttpServletRequest)request;
        String path = req.getServletPath();
        String serverIP = request.getLocalAddr();
        if (path.contains("index.jsp")) {
            req.setAttribute("ver", "7.0.1");
        }
        Log4j2Utils.putSeverletRequestIdToThreadContext();
        StopWatch stopWatch = null;
        boolean bl = isDownloadUpgradePackage = req.getRequestURL().toString().contains("Profile") || req.getRequestURL().toString().contains("Cert");
        if (isDownloadUpgradePackage) {
            DownloadLimiter.getInstance().increaseIpDownloadCount(request.getRemoteAddr(), serverIP);
            stopWatch = new StopWatch();
            stopWatch.start();
            response = new SimpleFileDownloadResponseWrapper((HttpServletResponse)response);
            LOG.info("TV >>>>>>>> SI IP = {} file:{} start to download.", (Object)req.getRemoteAddr(), (Object)req.getRequestURL());
        }
        chain.doFilter(request, response);
        if (isDownloadUpgradePackage) {
            DownloadLimiter.getInstance().decreaseIpDownloadCount(serverIP);
            stopWatch.stop();
            long fileLength = this.getDownloadFileContentLenth(req, response);
            long writtenLength = ((SimpleFileDownloadResponseWrapper)response).getContentWritten();
            String downloadFullFileResult = "download " + (fileLength <= writtenLength ? "success" : "fail");
            String downloadSpeed = "-";
            if (stopWatch.getTime() >= 1000L) {
                downloadSpeed = FormatUtil.formatBytes(writtenLength / (stopWatch.getTime() / 1000L)) + "/s";
            }
            LOG.info("SI >>>>>>>> TV IP = {} file:{} {},file-length:{} response-length:{} used time:{} download speed:{}", req.getRemoteAddr(), req.getRequestURL(), downloadFullFileResult, fileLength, writtenLength, stopWatch.getTime(), downloadSpeed);
        }
    }

    private long getDownloadFileContentLenth(HttpServletRequest request, ServletResponse response) {
        String downloadLength = ((HttpServletResponse)response).getHeader("content-length");
        if (downloadLength != null) {
            return Long.parseLong(downloadLength);
        }
        File file = new File(request.getServletContext().getRealPath(request.getServletPath()));
        if (file.exists()) {
            return file.length();
        }
        return -1L;
    }

    private class SimpleDownloadOutputStream
    extends ServletOutputStream {
        private final ServletOutputStream delegate;
        private SimpleFileDownloadResponseWrapper simpleFileDownloadResponseWrapper;

        public SimpleDownloadOutputStream(ServletOutputStream delegate, SimpleFileDownloadResponseWrapper simpleFileDownloadResponseWrapper) {
            this.delegate = delegate;
            this.simpleFileDownloadResponseWrapper = simpleFileDownloadResponseWrapper;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.simpleFileDownloadResponseWrapper.addContentWritten(len);
            this.delegate.write(b, off, len);
        }

        public boolean isReady() {
            return this.delegate.isReady();
        }

        public void setWriteListener(WriteListener writeListener) {
            this.delegate.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            this.delegate.write(b);
        }
    }

    private class SimpleFileDownloadResponseWrapper
    extends HttpServletResponseWrapper {
        private long contentWritten;

        public long getContentWritten() {
            return this.contentWritten;
        }

        public void addContentWritten(long contentWritten) {
            this.contentWritten += contentWritten;
        }

        public SimpleFileDownloadResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new SimpleDownloadOutputStream(super.getOutputStream(), this);
        }
    }
}

