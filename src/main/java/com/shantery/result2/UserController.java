package com.shantery.result2;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;
	@Autowired
	ShoutRepository shoutRepository;
	@Autowired
	ShoutService shoutService;

	//新規登録　入力画面
	@RequestMapping(value = "/in", method = RequestMethod.GET)
	public String input(User user) {
		return "input";
	}

	//新規登録　確認
	@RequestMapping(value = "/addcomfirm", method = RequestMethod.POST)
	public String addcomfirm(@Validated User user, BindingResult /* (3) */ result,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "input";
		}
		model.addAttribute("user", user);
		return "inputcomfirm";
	}

	@PostMapping(path = "/addcomfirm", params = "back")
	public ModelAndView addcomfirmback(ModelAndView mav,@ModelAttribute User user) {
		mav.setViewName("login");
		return mav;
    }
	@PostMapping(path = "/inputcomfirm", params = "back")
	public ModelAndView addresultback(ModelAndView mav,@ModelAttribute User user) {
		mav.addObject("user", user);
		mav.setViewName("inputcomfirm");
		return mav;
    }
	//新規登録　セット
	@RequestMapping(value = "/inputcomfirm", method = RequestMethod.POST)
	public String add(@Validated User user, BindingResult /* (3) */ result,@RequestParam String loginid,@RequestParam String password,@RequestParam String username,@RequestParam String icon,@RequestParam String profile,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "inputcomfirm";
		}
		User users=new User();
		users.setLoginid(loginid);
		users.setPassword(password);
		users.setUsername(username);
		users.setIcon(icon);
		users.setProfile(profile);
		session.setAttribute("loginid", loginid);
		session.setAttribute("password", password);
		userRepository.save(users);
		return "inputresult";
	}
	// 新規登録後　ログイン機能
	@RequestMapping(value = "/nexttop", method = RequestMethod.GET)
	public ModelAndView addtop(ModelAndView mav) {
		String loginid = (String) session.getAttribute("loginid");
		String password = (String) session.getAttribute("password");
		User user = userRepository.findByLoginidAndPassword(loginid, password);
		List<Shout> shout=shoutRepository.findAll();
		mav.addObject("shout",shout);
		mav.setViewName("top");
		mav.addObject("user", user);
		return mav;
	}
	@RequestMapping(value = "/researchinput", method = RequestMethod.GET)
	public String indexs(UserForm userForm) {
		return "researchinput";
	}
	@RequestMapping(value = "/addshout", method = RequestMethod.POST)
	public ModelAndView addshout(ModelAndView mav,@RequestParam String icon, @RequestParam String username,
			@RequestParam String shout) {
		Shout shouts=new Shout();
		shouts.setIcon(icon);
		shouts.setShout_username(username);
		shouts.setShout(shout);
		shoutRepository.save(shouts);
		List<Shout> shoutupdata=shoutRepository.findAll();
		mav.addObject("shout", shoutupdata);
		//登録 usertable loginid password session get
		String loginid=(String)session.getAttribute("loginids");
		String password=(String)session.getAttribute("passwords");
		User user = userRepository.findByLoginidAndPassword(loginid, password);
		mav.addObject("user", user);
		mav.setViewName("top");

		return mav;
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
		session.setAttribute("icon", icon);
		session.setAttribute("username", username);
		session.setAttribute("loginid", loginid);
		List<User> result = userRepository.findByUsernameOrIconOrLoginid(username, icon, loginid);
		mav.addObject("result", result);
		mav.addObject("resultSize", result.size());
		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(User user) {
		return "login.html";
	}

	//ログイン機能
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logininput(Model mav, @RequestParam String loginid, @RequestParam String password, @Validated User user, BindingResult /* (3) */ result) {

		List<Shout> shout=shoutRepository.findAll();
		mav.addAttribute("shout",shout);
		session.setAttribute("loginids", loginid);
		session.setAttribute("passwords", password);
		User users = userRepository.findByLoginidAndPassword(loginid, password);
		if (users == null) {
			mav.addAttribute("user", user);
			return "login";
		} else {
			session.setAttribute("login", loginid);
			session.setAttribute("pas", password);
			mav.addAttribute("user", users);
			return "top";
		}

	}
	@RequestMapping(value = "/shoutdelete", method = RequestMethod.POST)
	public String shoutdelete(@RequestParam Long[] shout_id,Model model) {
		session.setAttribute("shout_id", shout_id);
		ArrayList<Shout> list = new ArrayList<Shout>();
		for (int index = 0; index < shout_id.length; index ++){
			Long id=shout_id[index];
			Shout shout=shoutService.findOne(id);
			list.add(shout);
		}
		model.addAttribute("shout", list);
        return "shoutdeletecomfirm";
	}
	@RequestMapping(value = "/shoutdeleteresult", method = RequestMethod.POST)
	public String shoutdeleteresukt(Model model) {
		Long[] shout_id=(Long[])session.getAttribute("shout_id");
		for (int index = 0; index < shout_id.length; index ++){
			Long id=shout_id[index];
			shoutService.delete(id);
		}

        return "shoutdeleteresult";
	}
	@RequestMapping(value = "/userupdata", method = RequestMethod.POST)
	public String updata(@RequestParam String loginid,Model model) {
		User result=userRepository.findByLoginid(loginid);
		model.addAttribute("user", result);
        return "userupdatainput";
	}
	//削除処理
	@RequestMapping(value = "/userdelete", method = RequestMethod.POST)
	public String delete(@RequestParam String loginid,ModelAndView mav,@ModelAttribute UserForm userForm,Model model) {
		User result=userRepository.findByLoginid(loginid);
		model.addAttribute("user", result);
		return "userdeletecomfirm";
	}
	@RequestMapping(value = "/updatacomfirm", method = RequestMethod.POST)
	public ModelAndView updatacomfirm(ModelAndView mav,@ModelAttribute User user) {
		mav.addObject("user", user);
		mav.setViewName("userupdatacomfirm");
		return mav;
	}
	//戻るボタン処理
	@PostMapping(path = "/updataresult/{loginid}", params = "back")
	public ModelAndView Updatareturnback(ModelAndView mav,@ModelAttribute User user) {
		mav.addObject("user", user);
		mav.setViewName("userupdatainput");
		return mav;
    }
	@PostMapping(path = "/updeleteresult/{loginid}", params = "back")
	public ModelAndView Deletereturnback(ModelAndView mav,@ModelAttribute User user) {
		String icon = (String) session.getAttribute("icon");
		String username = (String) session.getAttribute("username");
		String loginid = (String) session.getAttribute("loginid");
		List<User> result = userRepository.findByUsernameOrIconOrLoginid(username, icon, loginid);
		mav.addObject("result", result);
		mav.addObject("resultSize", result.size());
		mav.setViewName("researchresult");
		return mav;
    }
	@PostMapping(path = "/shoutdeleteresult", params = "back")
	public ModelAndView ShoutDeletereturn(ModelAndView mav) {
		String loginid=(String)session.getAttribute("login");
		String password=(String)session.getAttribute("pas");
		User user = userRepository.findByLoginidAndPassword(loginid, password);
		List<Shout> shout=shoutRepository.findAll();
		mav.setViewName("top");
		mav.addObject("user", user);
		mav.addObject("shout", shout);
		return mav;
    }
	@GetMapping(path = "/returntop")
	public ModelAndView returntop(ModelAndView mav) {
		//ログイン時　loginid受け取り　password受け取り
		String loginid=(String)session.getAttribute("login");
		String password=(String)session.getAttribute("pas");
		User user = userRepository.findByLoginidAndPassword(loginid, password);
		mav.setViewName("top");
		List<Shout> shout=shoutRepository.findAll();
		mav.addObject("shout", shout);
		mav.addObject("user", user);
		return mav;
    }
	@PostMapping(path = "/updatacomfirm", params = "back")
	public ModelAndView comfirmback(ModelAndView mav) {
		String icon = (String) session.getAttribute("icon");
		String username = (String) session.getAttribute("username");
		String loginid = (String) session.getAttribute("loginid");
		List<User> result = userRepository.findByUsernameOrIconOrLoginid(username, icon, loginid);
		mav.addObject("result", result);
		mav.addObject("resultSize", result.size());
		mav.setViewName("researchresult");
		return mav;
    }
	@RequestMapping(value = "/updataresult/{loginid}", method = RequestMethod.POST)
	public String UserResult(@PathVariable String loginid,@ModelAttribute User user) {
		user.setLoginid(loginid);
		userService.update(user);
		return "userupdataresult";
	}
	@RequestMapping(value = "/updeleteresult/{loginid}", method = RequestMethod.POST)
	public String UserDeleteResult(@PathVariable String loginid,@ModelAttribute User user) {
		userService.delete(loginid);
		return "userdeleteresult";
	}

}
