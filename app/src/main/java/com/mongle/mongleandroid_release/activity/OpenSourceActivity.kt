package com.mongle.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.OpenSourceAdapter
import com.mongle.mongleandroid_release.network.data.OpenSourceData
import com.mongle.mongleandroid_release.util.OpenSourceDecoration
import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceActivity : AppCompatActivity() {

    lateinit var openSourceAdapter: OpenSourceAdapter
    val datas = mutableListOf<OpenSourceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        activity_open_source_btn_back.setOnClickListener {
            finish()
        }


        rv_open_source.addItemDecoration(OpenSourceDecoration(18))
        openSourceAdapter = OpenSourceAdapter(this)
        rv_open_source.adapter = openSourceAdapter
        loadDatas()

        openSourceAdapter.setItemClickListener(object : OpenSourceAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@OpenSourceActivity, OpenSourceDetailActivity::class.java)
                intent.putExtra("title", datas[position].title)
                intent.putExtra("content", datas[position].content)
                startActivity(intent)
            }

        })

    }

    private fun loadDatas() {
        datas.apply {
            add(
                OpenSourceData(
                    title = "Kotlin",
                    content = "Copyright 2000-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.\n" +
                            "\n" +
                            "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "   you may not use this file except in compliance with the License.\n" +
                            "   You may obtain a copy of the License at\n" +
                            "\n" +
                            "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "   Unless required by applicable law or agreed to in writing, software\n" +
                            "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "   See the License for the specific language governing permissions and\n" +
                            "   limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "Glide",
                    content = "Copyright 2014 Google, Inc. All rights reserved.\n" +
                            "\n" +
                            "Redistribution and use in source and binary forms, with or without modification, are\n" +
                            "permitted provided that the following conditions are met:\n" +
                            "\n" +
                            "   1. Redistributions of source code must retain the above copyright notice, this list of\n" +
                            "         conditions and the following disclaimer.\n" +
                            "\n" +
                            "   2. Redistributions in binary form must reproduce the above copyright notice, this list\n" +
                            "         of conditions and the following disclaimer in the documentation and/or other materials\n" +
                            "         provided with the distribution.\n" +
                            "\n" +
                            "THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED\n" +
                            "WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
                            "FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR\n" +
                            "CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
                            "CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
                            "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\n" +
                            "ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\n" +
                            "NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF\n" +
                            "ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."
                )
            )
            add(
                OpenSourceData(
                    title = "Glide Transformations",
                    content = "Copyright (C) 2020 Wasabeef\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "CircleImageView",
                    content = "Copyright 2014 - 2020 Henning Dodenhof\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "Retrofit",
                    content = "Copyright 2013 Square, Inc.\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "OkHttp",
                    content = "Copyright 2019 Square, Inc.\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "Gson",
                    content = "Copyright 2008 Google Inc.\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "FlowLayout",
                    content = "Copyright 2016 nex3z\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "StickyScrollView",
                    content = "MIT License\n" +
                            "\n" +
                            "Copyright (c) 2017 Amar Jain\n" +
                            "\n" +
                            "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                            "of this software and associated documentation files (the \"Software\"), to deal\n" +
                            "in the Software without restriction, including without limitation the rights\n" +
                            "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                            "copies of the Software, and to permit persons to whom the Software is\n" +
                            "furnished to do so, subject to the following conditions:\n" +
                            "\n" +
                            "The above copyright notice and this permission notice shall be included in all\n" +
                            "copies or substantial portions of the Software.\n" +
                            "\n" +
                            "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                            "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                            "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                            "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                            "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                            "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                            "SOFTWARE."
                )
            )
            add(
                OpenSourceData(
                    title = "SwitchButton",
                    content = "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "android-gif-drawable",
                    content = "MIT License\n" +
                            "Copyright (c) 2013 - present Karol Wrótniak, Droids on Roids LLC\n" +
                            "\n" +
                            "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                            "of this software and associated documentation files (the \"Software\"), to deal\n" +
                            "in the Software without restriction, including without limitation the rights\n" +
                            "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                            "copies of the Software, and to permit persons to whom the Software is\n" +
                            "furnished to do so, subject to the following conditions:\n" +
                            "\n" +
                            "The above copyright notice and this permission notice shall be included in\n" +
                            "all copies or substantial portions of the Software.\n" +
                            "\n" +
                            "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                            "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                            "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE\n" +
                            "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                            "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                            "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
                            "THE SOFTWARE.\n"
                )
            )
            add(
                OpenSourceData(
                    title = "Dots Indicator",
                    content = "Copyright 2016 Tommy Buonomo\n" +
                            "\n" +
                            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                            "you may not use this file except in compliance with the License.\n" +
                            "You may obtain a copy of the License at\n" +
                            "\n" +
                            "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                            "\n" +
                            "Unless required by applicable law or agreed to in writing, software\n" +
                            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                            "See the License for the specific language governing permissions and\n" +
                            "limitations under the License."
                )
            )
            add(
                OpenSourceData(
                    title = "ThemeImage",
                    content = "Pixabay\n" +
                            "https://pixabay.com/\n\n" +
                            "Unsplash\n" +
                            "https://unsplash.com/"
                )
            )

            openSourceAdapter.datas = datas
            openSourceAdapter.notifyDataSetChanged()
        }
    }
}