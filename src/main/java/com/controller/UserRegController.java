package com.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model.LoginPage;
import com.model.UserReg;
import com.service.UserService;
import com.sun.mail.util.MailConnectException;

@RestController

public class UserRegController {

	@Autowired
	private UserService use;
	Base64.Encoder encoder = Base64.getEncoder();
	Base64.Decoder decoder = Base64.getDecoder();

	/*
	 * @InitBinder protected void initBinder(WebDataBinder binder) {
	 * binder.addValidators(uvali); }
	 */
	// create user page
	@RequestMapping("/createuser")
	public ModelAndView create(@ModelAttribute("login") UserReg user) {

		ModelAndView mdv = new ModelAndView("createuser");
		mdv.addObject("login", new UserReg());
		return mdv;
	}
	@RequestMapping("/createadmin")
	public ModelAndView createAdmin(@ModelAttribute("Admin") UserReg admin) {
		
		List<String> ls = use.getUsernames();
		String s = "admin";
		for (String string : ls) {
			if(string.equalsIgnoreCase(s))
			{
				return new ModelAndView("redirect:/logi");
			}
			
		}
		ModelAndView mdv = new ModelAndView("adminpage");
		mdv.addObject("Admin", new UserReg());
		return mdv;
	}

	@RequestMapping(value = "/adtwo", method = RequestMethod.POST)
	public ModelAndView adminValid(@Valid @ModelAttribute("Admin") UserReg admin, BindingResult errors) {
		
		try{
			if (errors.hasErrors()) {
			return new ModelAndView("adminpage");
		} 
			else {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
			String time = df.format(date);
			String password = encoder.encodeToString(admin.getPass().getBytes());
			admin.setDob(time);
			admin.setPass(password);
			use.save(admin);
			String role = admin.getRole();
			System.out.println("role is " + role);
			/*
			 * HttpSession sesrole=req.getSession(); sesrole.setAttribute("role",role);
			 */
			System.out.println("encode password" + password);
			return new ModelAndView("redirect:/sucpage");
		}
		}
		catch (ConstraintViolationException cne) {
			if (errors.hasErrors()) {

				return new ModelAndView("adminpage");
			}

			else {
				cne.printStackTrace();
				return new ModelAndView("adminpage");
			}

		}
	}


	// save user
	@RequestMapping(value = "/lo", method = RequestMethod.POST)
	public ModelAndView login(@Valid @ModelAttribute("login") UserReg user, BindingResult errors) {

		try {
			if (errors.hasErrors()) {
				return new ModelAndView("createuser");
			} else {
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
				String time = df.format(date);
				String password = encoder.encodeToString(user.getPass().getBytes());
				user.setDob(time);
				user.setPass(password);
				user.setRole("user");
				use.save(user);
				/*
				 * String role = user.getRole(); System.out.println("role is " + role);
				 * 
				 * HttpSession sesrole=req.getSession(); sesrole.setAttribute("role",role);
				 */
				System.out.println("encode password" + password);
				return new ModelAndView("redirect:/sucpage");
			}
		}

		catch (ConstraintViolationException cne) {
			if (errors.hasErrors()) {

				return new ModelAndView("createuser");
			}

			else {
				cne.printStackTrace();
				return new ModelAndView("createuser");
			}

		}
	}

	@RequestMapping(value = "/lo", method = RequestMethod.GET)
	public ModelAndView logintwo(@ModelAttribute("login") UserReg user) {

		return new ModelAndView("createuser");
		/*
		 * Date date = new Date(); DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
		 * String time=df.format(date); String password
		 * =encoder.encodeToString(user.getPass().getBytes()); user.setDob(time);
		 * user.setPass(password); use.save(user); String role =user.getRole();
		 * System.out.println("role is "+role);
		 * 
		 * HttpSession sesrole=req.getSession(); sesrole.setAttribute("role",role);
		 * 
		 * System.out.println("encode password"+password); return new
		 * ModelAndView("redirect:/logi");
		 */
	}

	// login page
	@RequestMapping("/logi")
	public ModelAndView logi(@ModelAttribute("login") LoginPage logie) {
		return new ModelAndView("login");
	}

	@RequestMapping("/sucpage")
	public ModelAndView success() {
		return new ModelAndView("spage");
	}

	// verify login

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ModelAndView ver(@ModelAttribute("login") LoginPage loge) {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView verify(@Valid @ModelAttribute("login") LoginPage loge, BindingResult errors,
			HttpServletRequest req)

	{
		String msgo = "username mismatch";
		String msgp = "password mismatch";
		try {

			if (errors.hasErrors()) {
				return new ModelAndView("login");
			} else {

				String name = loge.getUsName();
				System.out.println("uname is " + name);

				/*
				 * if(name.isEmpty()&&user.getPass().isEmpty()||(name.isEmpty()||user.getPass().
				 * isEmpty())) { ModelAndView mdv = new ModelAndView("login");
				 * mdv.addObject("username",msgo); mdv.addObject("password",msgp); return mdv; }
				 * 
				 * else {
				 */
				UserReg ug = use.findByName(name);
				int idgen = ug.getuId();
				HttpSession ses = req.getSession();
				ses.setAttribute("idgen", idgen);
				System.out.println("uid from verify :" + idgen);
				String passdecode = new String(decoder.decode(ug.getPass()));
				System.out.println("user id" + ug.getuId());
				HttpSession session = req.getSession();
				session.setAttribute("userReg", ug);
				req.setAttribute("newusereg", ug);
				String password = loge.getPasswrd();

				HttpSession sesroles = req.getSession();
				HttpSession sesroles1 = req.getSession();
				sesroles.setAttribute("nameuser", ug.getuName());
				sesroles1.setAttribute("role", ug.getRole());

				System.out.println("decrypted password :" + passdecode);

				/*
				 * session=req.getSession(); session.setAttribute("idval", ug);
				 */
				System.out.println("database psd=" + ug.getPass() + "\n" + "text box psd=" + password);
				if (password.equals(passdecode)) {
					if (ug.getRole().equalsIgnoreCase("user")) {
						return new ModelAndView("redirect:user");
					} else {
						return new ModelAndView("redirect:admin");
					}
				} else {
					ModelAndView mdv = new ModelAndView("login");

					mdv.addObject("password", msgp);
					return mdv;
				}
				/*
				 * catch(NullPointerException ne)
				 * 
				 * { return new ModelAndView("login");
				 */
			}

		} catch (NullPointerException ne) {
			ModelAndView mdv = new ModelAndView("login");
			mdv.addObject("username", msgo);
			mdv.addObject("password", msgp);

			return mdv;
		}
	}
	
	//forgot password
	@RequestMapping("/forgotpassword")
	public ModelAndView forgotPas(@ModelAttribute("login") LoginPage user)
	{
		String s = "vale";
		ModelAndView mdc = new ModelAndView("login");
		mdc.addObject("eql",s);
		
		return mdc;
		
	}
	//send mail forgot password
	@RequestMapping("/sendmail")
	public ModelAndView sendMail(@ModelAttribute("login") LoginPage log,HttpServletRequest req)
	{
		
		
		String s = "User Doesn't Exists";
		String st = "vale";
		String es ="EmailSent";
		
		
		String name=log.getUsName();
		if(name==null)
		{
			return new ModelAndView("redirect:/forgotpassword");
		}
		else
		{
		
		UserReg ugs = use.findByName(name);
		
		if(ugs == null)
		{
			
			ModelAndView mdv = new ModelAndView("login");
			mdv.addObject("eql",st);
			mdv.addObject("notexist",s);
			return mdv;
			
			
		}else
		{
		String passdecode = new String(decoder.decode(ugs.getPass()));
		System.out.println("Password is "+passdecode);
		
		try {
	         URL url = new URL("http://www.gmail.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	         System.out.println("Internet is connected");
	      
		final String username = "bookmyshowapp.in@gmail.com";
		final String password = "Qwerty123#";
		String fromEmail = "bookmyshowapp.in@gmail.com";
		String toEmail = ugs.getMailId();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		
		MimeMessage mime = new MimeMessage(session);
		try {
			mime.setFrom(new InternetAddress(fromEmail));
			mime.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail) );
			mime.setSubject("Book My Show user Password");
			mime.setText("Hi User,Here is your User Id for log in to Book My Show Application ."+passdecode);
			Transport.send(mime);
			System.out.println("message sent");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (MalformedURLException e) {
	         System.out.println("Internet is not connected");
	         ModelAndView mbb = new ModelAndView("login");
	         mbb.addObject("netcheck","icheck");
	         return mbb;
	      } catch (IOException e) {
	         System.out.println("Internet is not connected");
	         ModelAndView mbb = new ModelAndView("login");
	         mbb.addObject("netcheck","icheck");
	         return mbb;
	      }
	
		}
		
		
		ModelAndView mvb = new  ModelAndView("/login");
		mvb.addObject("emailsent", es);
		return mvb;
		
		}
	}
	
	//forgot userid send mail
	@RequestMapping("/fogotuserid")
	public ModelAndView forgotUserId(@ModelAttribute("login") LoginPage lPage)
	{
		String sd = "userId";
		ModelAndView mdv = new ModelAndView("login");
		mdv.addObject("sv",sd);
		return mdv;
	}
	@RequestMapping("/useridemail")
	public ModelAndView emailUser(@ModelAttribute("login") LoginPage lpg,HttpServletRequest req)
	{
		
				String email =req.getParameter("email");
				UserReg ema = use.findEmail(email);
				String svv = "UserIdSent";
				if(ema.getuName() != null)
				{
					try {
				         URL url = new URL("http://www.gmail.com");
				         URLConnection connection = url.openConnection();
				         connection.connect();
				         System.out.println("Internet is connected");
				      
					
					
					final String username = "bookmyshowapp.in@gmail.com";
					final String password = "Qwerty123#";
					String fromEmail = "bookmyshowapp.in@gmail.com";
					String toEmail = ema.getMailId();
					
					Properties properties = new Properties();
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.starttls.enable", "true");
					properties.put("mail.smtp.host", "smtp.gmail.com");
					properties.put("mail.smtp.port", "587");
					
					Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,password);
						}
					});
					
					MimeMessage mime = new MimeMessage(session);
					try {
						mime.setFrom(new InternetAddress(fromEmail));
						mime.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail) );
						mime.setSubject("Book My Show user Password");
						mime.setText("Hi User,Here is your User Id for log in to Book My Show Application ."+ema.getuName());
						Transport.send(mime);
						System.out.println("message sent");
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						System.out.println("Connect to Internet");
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					} catch (MalformedURLException e) {
				         System.out.println("Internet is not connected");
				         ModelAndView mbb = new ModelAndView("login");
				         mbb.addObject("netcheck","icheck");
				         return mbb;
				      } catch (IOException e) {
				         System.out.println("Internet is not connected");
				         ModelAndView mbb = new ModelAndView("login");
				         mbb.addObject("netcheck","icheck");
				         return mbb;
				      }
				   }
				
					
					ModelAndView mbn = new ModelAndView("login");
					mbn.addObject("env",svv);
				
		return mbn;
	}
	
}
