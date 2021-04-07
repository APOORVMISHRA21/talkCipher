package Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragments.callFragment;
import Fragments.chatFragment;
import Fragments.cipherFragment;

public class FragmentAdapter  extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
            default:
                return new chatFragment();

            case 1:
                return new callFragment();

            case 2:
                return new cipherFragment();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch(position)
        {
            case 0:
            default:
                title = "chat";
                break;
            case 1:
                title="call";
                break;

            case 2:
                title = "cipher";
                break;
        }
        return title;
    }
}
