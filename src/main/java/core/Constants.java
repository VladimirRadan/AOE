package core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static utils.Utils.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {


    public static final String DEV_URL = dotEnv().get("DEV_URL");


}//end class
