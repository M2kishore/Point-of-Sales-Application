package com.increff.employee.controller.api;

import com.increff.employee.model.data.InvoiceData;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.OrderInvoiceData;
import com.increff.employee.model.form.BillForm;
import com.increff.employee.model.form.BillFormList;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.DateUtil;
import com.increff.employee.model.data.OrderItemData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;

@Api
@RestController
public class OrderApiController {
    private static Logger logger = Logger.getLogger(OrderApiController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value="Gets a product by Barcode")
    @RequestMapping(path="/api/order",method = RequestMethod.GET)
    public OrderItemData get(@RequestParam String barcode) throws ApiException {
        ProductPojo productPojo = productService.getBarcode(barcode);
        return convertPojoToData(productPojo);
    }
    @ApiOperation(value="Gets a products by orderId")
    @RequestMapping(path="/api/order/{id}",method = RequestMethod.GET)
    public List<BillForm> get(@PathVariable int id) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderService.select(id);
        List<BillForm> billFormList = new ArrayList<BillForm>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            BillForm billForm = new BillForm();
            billForm.setBarcode(productPojo.getBarcode());
            billForm.setMrp(productPojo.getMrp());
            billForm.setName(productPojo.getName());
            billForm.setQuantity(orderItemPojo.getQuantity());
            billForm.setSellingPrice(orderItemPojo.getSellingPrice());
            billForm.setProductId(orderItemPojo.getProductId());

            billFormList.add(billForm);
        }
        return billFormList;
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/order/all", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        List<OrderItemPojo> list = orderService.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderItemPojo orderItemPojo : list) {
            list2.add(convertPojoToData(orderItemPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/order/all/id", method = RequestMethod.GET)
    public List<OrderInvoiceData> getAllIds() {
        List<OrderPojo> list = orderService.getAllIds();
        List<OrderInvoiceData> list2 = new ArrayList<OrderInvoiceData>();
        for (OrderPojo orderPojo : list) {
            list2.add(convertPojoToData(orderPojo));
        }
        return list2;
    }

    private OrderInvoiceData convertPojoToData(OrderPojo orderPojo) {
        OrderInvoiceData orderInvoiceData = new OrderInvoiceData();
        orderInvoiceData.setId(orderPojo.getId());
        orderInvoiceData.setDate(DateUtil.DateToMillisecond(orderPojo.getDate()));
        return orderInvoiceData;
    }

    @ApiOperation(value = "gets PDF of invoice")
    @RequestMapping(path = "api/order/pdf",method = RequestMethod.POST)
    protected void makePdf(@RequestBody BillFormList billFormList, HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String billFormListXml = jaxbObjectToXML(billFormList);
        logger.info(billFormListXml);
        //creating the instance of file
        File path = new File(System.getProperty("user.dir")+"/src/main/resources/bill.xml");

        //passing file instance in filewriter
        FileWriter wr = new FileWriter(path);

        //calling writer.write() method with the string
        wr.write(billFormListXml);

        //flushing the writer
        wr.flush();

        //closing the writer
        wr.close();
        try{
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

            //Setup a buffer to obtain the content length
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource("src/main/resources/template.xsl"));
            //Make sure the XSL transformation's result is piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            //Setup input
            Source src = new StreamSource(new File(System.getProperty("user.dir")+"/src/main/resources/bill.xml"));

            //Start the transformation and rendering process
            transformer.transform(src, res);

            //Prepare response
            response.setContentType("application/pdf");
            response.setContentLength(out.size());

            //Send content to Browser
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @ApiOperation(value = "add date and time for orders")
    @RequestMapping(path="/api/order",method = RequestMethod.POST)
    public int add(@RequestBody OrderForm form)throws ApiException{
        OrderPojo newOrderPojo = convertFormToPojo(form);
        return orderService.add(newOrderPojo);
    }

    @ApiOperation(value = "Place an order")
    @RequestMapping(path = "/api/order/order",method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form)throws ApiException{
        OrderItemPojo newOrderItemPojo = convertFormToPojo(form);
        orderService.add(newOrderItemPojo);
    }



    private OrderItemPojo convertFormToPojo(OrderItemForm orderItemForm){
        OrderItemPojo newOrderItemPojo = new OrderItemPojo();
        newOrderItemPojo.setOrderId(orderItemForm.getOrderId());
        newOrderItemPojo.setProductId(orderItemForm.getProductId());
        newOrderItemPojo.setQuantity(orderItemForm.getQuantity());
        newOrderItemPojo.setSellingPrice(orderItemForm.getSellingPrice());
        return newOrderItemPojo;
    }
    private OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo newOrderPojo = new OrderPojo();
        newOrderPojo.setDate(DateUtil.MillisecondToDate(orderForm.getDate()));
        return newOrderPojo;
    }
    private OrderItemData convertPojoToData(ProductPojo productPojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setName(productPojo.getName());
        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setMrp(productPojo.getMrp());
        orderItemData.setProductId(productPojo.getId());
        return orderItemData;
    }

    private OrderData convertPojoToData(OrderItemPojo orderItemPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderItemPojo.getId());
        orderData.setOrderId(orderItemPojo.getOrderId());
        orderData.setProductId(orderItemPojo.getProductId());
        orderData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderData;
    }

    private static String jaxbObjectToXML(BillFormList billFormList)
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(BillFormList.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(billFormList, sw);

            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }
}
