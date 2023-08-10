import React from "react";
import DehazeIcon from "@mui/icons-material/Dehaze";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import MailIcon from "@mui/icons-material/Mail";
import { Button } from "@mui/material";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";

import logoImg from "../../assets/img/logo.png";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import { SignInState, SubCheckState } from "../../recoil/SignInState";
import axios from "axios";

function Header(props) {
  const navi = useNavigate();
  const signIn = () => {
    navi("/auth/sign-in");
  };
  const isLogin = useRecoilValue(SignInState) === "" ? false : true;
  const isSub = useRecoilValue(SubCheckState);
  const subscription = () => {
    if (isLogin) {
      if (isSub) {
        navi("/sub-info"); // 이미 구독중인 사용자가 구독신청을 누르면 → 마이페이지 구독정보로 이동
        return;
      }
      navi("/subscription");
    } else {
      navi("/auth/sign-in");
    }
  };
  const main = () => {
    navi("/");
  };

  const [memberId, setMemberId] = useRecoilState(SignInState);

  const [state, setState] = React.useState({
    right: false,
  });

  const toggleDrawer = (anchor, open) => (event) => {
    if (
      event.type === "keydown" &&
      (event.key === "Tab" || event.key === "Shift")
    ) {
      return;
    }

    setState({ state, [anchor]: open });
  };

  const logout = () => {
    axios({
      method: "GET",
      url: "/auth/log-out",
    })
      .then((response) => {
        setMemberId("");
        navi("/");
      })
      .catch((err) => {
        console.log(memberId);
        console.log(err);
      });
  };

  const list = (anchor) => (
    <Box
      sx={{
        width: anchor === "top" || anchor === "bottom" ? "auto" : 250,
      }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List>
        <ListItem disablePadding onClick={subscription}>
          <ListItemButton>
            <ListItemIcon>
              <MailIcon />
            </ListItemIcon>
            <ListItemText primary="구독신청" />
          </ListItemButton>
        </ListItem>
      </List>

      <List>
        <ListItem disablePadding>
          <ListItemButton>
            <ListItemIcon>
              <MailIcon />
            </ListItemIcon>
            <ListItemText primary="공지사항" />
          </ListItemButton>
        </ListItem>
      </List>

      <List>
        <ListItem disablePadding>
          <ListItemButton>
            <ListItemIcon>
              <MailIcon />
            </ListItemIcon>
            <ListItemText primary="FAQ" />
          </ListItemButton>
        </ListItem>
      </List>

      <List>
        <ListItem disablePadding onClick={logout}>
          <ListItemButton>
            <ListItemIcon>
              <MailIcon />
            </ListItemIcon>
            <ListItemText primary="로그아웃" />
          </ListItemButton>
        </ListItem>
      </List>
      <Divider />
    </Box>
  );

  return (
    <>
      <div className="nav">
        <img
          alt=""
          src={logoImg}
          width={"30%"}
          style={{ float: "left", cursor: "pointer" }}
          onClick={main}
        ></img>

        <Box sx={{ display: "flex", alignItems: "center" }}>
          {memberId ? (
            <NotificationsNoneIcon sx={{ marginRight: 2 }} />
          ) : (
            <Button variant="contained" onClick={signIn}>
              로그인
            </Button>
          )}

          {["right"].map((anchor) => (
            <React.Fragment key={anchor}>
              <DehazeIcon
                sx={{ cursor: "pointer", marginLeft: 3 }}
                onClick={toggleDrawer(anchor, true)}
              >
                {anchor}
              </DehazeIcon>
              <Drawer
                anchor={anchor}
                open={state[anchor]}
                onClose={toggleDrawer(anchor, false)}
              >
                {list(anchor)}
              </Drawer>
            </React.Fragment>
          ))}
        </Box>
      </div>
    </>
  );
}

export default Header;
