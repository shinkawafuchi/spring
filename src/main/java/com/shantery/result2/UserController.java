package com.shantery.result2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	//新規登録　入力画面
	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String input(UserForm userForm) {
		return "input";
	}

	//新規登録　確認
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String comfirm(@RequestParam String loginid, @RequestParam String password, @RequestParam String username,
			@RequestParam String icon, @RequestParam String profile, UserForm userForm) {
		return "addresult";
	}

	//新規登録　セット
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String add(@RequestParam String loginid, @RequestParam String password, @RequestParam String username,
			@RequestParam String icon, @RequestParam String profile) {
		User user = new User();
		user.setLoginid(loginid);
		user.setPassword(password);
		user.setIcon(icon);
		user.setProfile(profile);
		user.setUsername(username);
		userRepository.save(user);
		return "addcomfirm";
	}

	@RequestMapping(value = "/researchinput", method = RequestMethod.GET)
	public String indexs(UserForm userForm) {
		return "researchinput";
	}

	@GetMapping("todo/find")
	public String find() {
		return "researchresult";
	}

	//検索結果 機能
	@RequestMapping(value = "/researchresult", method = RequestMethod.POST)
	public ModelAndView Research(ModelAndView mav, @RequestParam String icon, @RequestParam String username,
			@RequestParam String loginid) {
		mav.setViewName("researchresult");
		mav.addObject("icon", icon);
		mav.addObject("username", username);
		List<User> result = userRepository.findByUsernameOrIconOrLoginid(username, icon, loginid);
		mav.addObject("result", result);
		mav.addObject("resultSize", result.size());
		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(User user) {
		return "login.html";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String boot(User user) {
		return "index.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView logininput(ModelAndView mav, @RequestParam String loginid, @RequestParam String password) {
		User user = userRepository.findByLoginidAndPassword(loginid, password);
		if (user == null) {
			mav.setViewName("index");
			mav.addObject("user", user);
			return mav;
		} else {
			mav.setViewName("top");
			mav.addObject("user", user);
			return mav;
		}

	}

	@RequestMapping(value = "/user/{user.loginid}", method = RequestMethod.GET)
	public String updata(@PathVariable String loginid, Model model) {
		User user = userRepository.findByLoginid(loginid);
		return "index.html";
	}

}
