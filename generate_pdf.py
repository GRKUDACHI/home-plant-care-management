#!/usr/bin/env python3
"""
Script to convert Markdown documentation to PDF
Requires: pip install markdown pdfkit
"""

import markdown
import pdfkit
import os
from pathlib import Path

def markdown_to_pdf(markdown_file, output_file):
    """Convert Markdown file to PDF"""
    
    # Read the markdown file
    with open(markdown_file, 'r', encoding='utf-8') as f:
        markdown_content = f.read()
    
    # Convert markdown to HTML
    html = markdown.markdown(
        markdown_content,
        extensions=[
            'markdown.extensions.tables',
            'markdown.extensions.fenced_code',
            'markdown.extensions.codehilite',
            'markdown.extensions.toc',
            'markdown.extensions.attr_list'
        ]
    )
    
    # Add CSS styling
    css_style = """
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        h1, h2, h3, h4, h5, h6 {
            color: #2c3e50;
            margin-top: 30px;
            margin-bottom: 15px;
        }
        h1 {
            border-bottom: 3px solid #3498db;
            padding-bottom: 10px;
        }
        h2 {
            border-bottom: 2px solid #ecf0f1;
            padding-bottom: 5px;
        }
        code {
            background-color: #f8f9fa;
            padding: 2px 4px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
        }
        pre {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            overflow-x: auto;
            border-left: 4px solid #3498db;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        blockquote {
            border-left: 4px solid #3498db;
            margin: 20px 0;
            padding: 10px 20px;
            background-color: #f8f9fa;
        }
        .toc {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin: 20px 0;
        }
        .page-break {
            page-break-before: always;
        }
    </style>
    """
    
    # Combine HTML with CSS
    full_html = f"""
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>Home Care Plants - Complete Project Documentation</title>
        {css_style}
    </head>
    <body>
        {html}
    </body>
    </html>
    """
    
    # Configure PDF options
    options = {
        'page-size': 'A4',
        'margin-top': '0.75in',
        'margin-right': '0.75in',
        'margin-bottom': '0.75in',
        'margin-left': '0.75in',
        'encoding': "UTF-8",
        'no-outline': None,
        'enable-local-file-access': None
    }
    
    try:
        # Convert HTML to PDF
        pdfkit.from_string(full_html, output_file, options=options)
        print(f"✅ PDF generated successfully: {output_file}")
        return True
    except Exception as e:
        print(f"❌ Error generating PDF: {e}")
        print("💡 Make sure you have wkhtmltopdf installed:")
        print("   Windows: Download from https://wkhtmltopdf.org/downloads.html")
        print("   Or install via chocolatey: choco install wkhtmltopdf")
        return False

def main():
    """Main function"""
    markdown_file = "COMPLETE_PROJECT_DOCUMENTATION.md"
    output_file = "Home_Care_Plants_Complete_Documentation.pdf"
    
    if not os.path.exists(markdown_file):
        print(f"❌ Markdown file not found: {markdown_file}")
        return
    
    print(f"🔄 Converting {markdown_file} to PDF...")
    
    if markdown_to_pdf(markdown_file, output_file):
        print(f"📄 PDF documentation created: {output_file}")
        print(f"📁 File size: {os.path.getsize(output_file)} bytes")
    else:
        print("❌ Failed to create PDF documentation")

if __name__ == "__main__":
    main()
