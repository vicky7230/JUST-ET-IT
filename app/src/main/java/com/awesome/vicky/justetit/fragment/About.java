package com.awesome.vicky.justetit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awesome.vicky.justetit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {


    public About() {
        // Required empty public constructor
    }

    private TextView textViewRecipeIcon;
    private TextView textViewLeftoversIcon;
    private TextView textViewHelpIcon;
    private TextView textViewAboutIcon;
    private TextView textViewRefreshIcon;
    private TextView textViewNoResultIcon;
    private TextView textViewAppIcon;
    private TextView textViewAppInto1Icon;
    private TextView textViewAppIntro2Icon;
    private TextView textViewAppIntro3Icon;
    private TextView textViewRecipyPuppyLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textViewRecipeIcon = (TextView) getView().findViewById(R.id.recipe_icon_textview);
        textViewLeftoversIcon = (TextView) getView().findViewById(R.id.leftovers_icon_textview);
        textViewHelpIcon = (TextView) getView().findViewById(R.id.help_icon_textview);
        textViewAboutIcon = (TextView) getView().findViewById(R.id.about_icon_textview);
        textViewRefreshIcon = (TextView) getView().findViewById(R.id.refresh_icon_textview);
        textViewNoResultIcon = (TextView) getView().findViewById(R.id.no_result_icon_textview);
        textViewAppIcon = (TextView) getView().findViewById(R.id.app_icon_textview);
        textViewAppInto1Icon = (TextView) getView().findViewById(R.id.app_intro1_textview);
        textViewAppIntro2Icon = (TextView) getView().findViewById(R.id.app_intro2_textview);
        textViewAppIntro3Icon = (TextView) getView().findViewById(R.id.app_intro3_textview);
        textViewRecipyPuppyLink = (TextView) getView().findViewById(R.id.api_textview);

        textViewRecipeIcon.setText(Html.fromHtml("Icon made by <a href='http://www.freepik.com' title='Freepik'>Freepik</a> from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewRecipeIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewLeftoversIcon.setText(Html.fromHtml("Icon made by <a href='http://www.freepik.com' title = 'Freepik' > Freepik </a > from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewLeftoversIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHelpIcon.setText(Html.fromHtml("Icon made by <a href='http://www.danielbruce.se' title='Daniel Bruce'>Daniel Bruce</a> from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewHelpIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAboutIcon.setText(Html.fromHtml("Icon made by <a href='http://www.freepik.com' title='Freepik'>Freepik</a> from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewAboutIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewRefreshIcon.setText(Html.fromHtml("Icon made by <a href='http://icon-works.com' title='Icon Works'>Icon Works</a> from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewRefreshIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewNoResultIcon.setText(Html.fromHtml("Icon made by <a href='http://catalinfertu.com' title='Catalin Fertu'>Catalin Fertu</a> from <a href='http://www.flaticon.com' title='Flaticon'>www.flaticon.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewNoResultIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAppIcon.setText(Html.fromHtml("Icon made by <a href='http://www.iconfinder.com/ArtWhite' title='Artem White'>Artem White</a> from <a href='http://www.iconfinder.com' title='iconfinder'>www.iconfinder.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewAppIcon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAppInto1Icon.setText(Html.fromHtml("Icon made by <a href='http://www.iconfinder.com/Squid.ink' title='Squid.ink'>Squid.ink</a> from <a href='http://www.iconfinder.com' title='iconfinder'>www.iconfinder.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewAppInto1Icon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAppIntro2Icon.setText(Html.fromHtml("Icon made by <a href='http://www.iconfinder.com/Squid.ink' title='Squid.ink'>Squid.ink</a> from <a href='http://www.iconfinder.com' title='iconfinder'>www.iconfinder.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewAppIntro2Icon.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAppIntro3Icon.setText(Html.fromHtml("Icon made by <a href='http://www.iconfinder.com/Squid.ink' title='Squid.ink'>Squid.ink</a> from <a href='http://www.iconfinder.com' title='iconfinder'>www.iconfinder.com</a> is licensed under <a href='http://creativecommons.org/licenses/by/3.0/' title='Creative Commons BY 3.0'>CC BY 3.0</a>"));
        textViewAppIntro3Icon.setMovementMethod(LinkMovementMethod.getInstance());

        textViewRecipyPuppyLink.setText(Html.fromHtml("Powered by <a href='http://www.recipepuppy.com/about/api/'>Recippy Puppy</a>"));
        textViewRecipyPuppyLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
